package clock.wise.service.impl;

import clock.wise.dao.UserDao;
import clock.wise.dto.UserDto;
import clock.wise.model.User;
import clock.wise.model.roles.Role;
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
    private UserDao userDao;
    @Autowired
    private UserModelMapperUtils userModelMapper;

    @Override
    @Transactional
    public UserDto createOrUpdateUser( final UserDto userDto )
    {
        validateUserPassword( userDto );

        User user = userModelMapper.getModelMapper().map( userDto, User.class );
        logger.info( "User " + user.getUsername() + " has been created" );

        return userModelMapper.getModelMapper().map( userDao.save( user ), UserDto.class );
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

    private void validateUserPassword( final UserDto userDto )
    {
        String userPassword = userDto.getPassword();
        PasswordUtils passwordUtils = new PasswordUtils();
        passwordUtils.validatePassword( userPassword );

        String hashPassword = passwordUtils.encodePasswordWithBCryptPasswordEncoder( userPassword );
        userDto.setPassword( hashPassword );
    }
}
