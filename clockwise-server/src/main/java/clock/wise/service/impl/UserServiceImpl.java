package clock.wise.service.impl;

import clock.wise.dao.UserDao;
import clock.wise.dto.PasswordDto;
import clock.wise.dto.UserDto;
import clock.wise.enums.MailTemplateEnum;
import clock.wise.exceptions.InvalidPasswordException;
import clock.wise.model.User;
import clock.wise.model.roles.Role;
import clock.wise.service.MailService;
import clock.wise.service.UserService;
import clock.wise.utils.PasswordUtils;
import clock.wise.utils.UserModelMapperUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    private final static Logger logger = Logger.getLogger( UserServiceImpl.class );

    @Autowired
    private PasswordUtils passwordUtils;
    @Autowired
    private UserModelMapperUtils userModelMapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MailService mailService;

    @Override
    @Transactional
    public UserDto createOrUpdateUser( final UserDto userDto )
    {
        String password = userDto.getPassword();

        validateUserPassword( password );
        userDto.setPassword( hashUserPassword( password ) );

        User user = userModelMapper.getModelMapper().map( userDto, User.class );
        User saved = userDao.save( user );
        UserDto created = userModelMapper.getModelMapper().map( saved, UserDto.class );

        if ( userDao.exists( saved.getId() ) )
        {
            logger.info( "User " + created.getUsername() + " has been created" );
            mailService.sendMessage( created, MailTemplateEnum.NEW_USER_REGISTERED );
        }

        return created;
    }

    @Override
    @Transactional
    public UserDto findById( final Long id )
    {
        return userModelMapper.getModelMapper().map( userDao.findOne( id ), UserDto.class );
    }

    @Override
    @Transactional
    public UserDto findByUsername( final String username )
    {
        if ( username == null || username.isEmpty() )
        {
            logger.error( "Error while searching user by username: " + username );
            throw new IllegalArgumentException( "Username cannot be null or empty" );
        }

        return userModelMapper.getModelMapper().map( userDao.findOneByUsername( username ), UserDto.class );
    }

    @Override
    @Transactional
    public UserDto findByRole( final Role role )
    {
        if ( role == null )
        {
            logger.error( "Error while searching user by role: " + role );
            throw new IllegalArgumentException( "Role cannot be null" );
        }

        return userModelMapper.getModelMapper().map( userDao.findOneByRole( role ), UserDto.class );
    }

    @Override
    @Transactional
    public List< UserDto > findAll()
    {
        List< UserDto > userDtoList = new ArrayList<>();

        Iterable< User > users = userDao.findAll();
        for ( User user : users )
        {
            UserDto userDto = userModelMapper.getModelMapper().map( user, UserDto.class );
            userDtoList.add( userDto );
        }

        return userDtoList;
    }

    @Override
    @Transactional
    public void removeUser( final Long id )
    {
        userDao.delete( id );
        logger.info( "User: " + id + " removed" );
    }

    @Override
    @Transactional
    public void changePassword( final PasswordDto passwordDto )
    {
        User user = userDao.findOne( passwordDto.getUserId() );
        String currentPassword = user.getPassword();

        if ( !passwordUtils.matches( passwordDto.getOldPassword(), currentPassword ) )
        {
            throw new InvalidPasswordException( "Given password is not equals to the current user password" );
        }

        String newPassword = passwordDto.getNewPassword();
        validateUserPassword( newPassword );
        user.setPassword( hashUserPassword( newPassword ) );

        UserDto userDto = userModelMapper.getModelMapper().map( userDao.save( user ), UserDto.class );
        mailService.sendMessage( userDto, MailTemplateEnum.USER_PASSWORD_UPDATE );
    }

    private void validateUserPassword( final String userPassword )
    {
        passwordUtils.validatePassword( userPassword );
    }

    private String hashUserPassword( final String userPassword )
    {
        return passwordUtils.encodePasswordWithBCryptPasswordEncoder( userPassword );
    }
}
