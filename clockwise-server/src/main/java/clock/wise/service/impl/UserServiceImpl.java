package clock.wise.service.impl;

import clock.wise.dao.CompanyDao;
import clock.wise.dao.UserDao;
import clock.wise.dto.PasswordDto;
import clock.wise.dto.UserDto;
import clock.wise.enums.MailTemplateEnum;
import clock.wise.exceptions.InvalidPasswordException;
import clock.wise.mapper.UserModelMapperWrapper;
import clock.wise.model.User;
import clock.wise.model.roles.Role;
import clock.wise.service.MailService;
import clock.wise.service.UserService;
import clock.wise.utils.LongUtils;
import clock.wise.utils.MapUtils;
import clock.wise.utils.PasswordUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final static Logger logger = Logger.getLogger( UserServiceImpl.class );

    @Autowired
    private PasswordUtils passwordUtils;
    @Autowired
    private UserModelMapperWrapper userModelMapperWrapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private MailService mailService;

    @Override
    @Transactional
    public UserDto createOrUpdate( final UserDto userDto ) {
        String password = userDto.getPassword();

        validateUserPassword( password );
        userDto.setPassword( hashUserPassword( password ) );

        User user = userModelMapperWrapper.getModelMapper().map( userDto, User.class );
        user.setCompany( companyDao.findOne( userDto.getCompanyId() ) );
        User saved = userDao.save( user );
        UserDto created = userModelMapperWrapper.getModelMapper().map( saved, UserDto.class );

        if ( userDao.exists( saved.getId() ) ) {
            logger.info( "User " + created.getUsername() + " has been created" );

            MapUtils.fillMapWithParams( "username", created.getUsername() );
            MapUtils.fillMapWithParams( "email", created.getEmail() );
            mailService.sendMessage( MapUtils.getMap(), MailTemplateEnum.NEW_USER_REGISTERED );
        }

        return created;
    }

    @Override
    @Transactional
    public UserDto findById( final Long id ) {
        if ( LongUtils.isEmpty( id ) ) {
            logger.error( "Error while searching user by id: " + id );
            throw new IllegalArgumentException( "Id cannot be null" );
        }

        return userModelMapperWrapper.getModelMapper().map( userDao.findOne( id ), UserDto.class );
    }

    @Override
    @Transactional
    public UserDto findByUsername( final String username ) {
        if ( StringUtils.isEmpty( username ) ) {
            logger.error( "Error while searching user by username: " + username );
            throw new IllegalArgumentException( "Username cannot be null or empty" );
        }

        return userModelMapperWrapper.getModelMapper().map( userDao.findOneByUsername( username ), UserDto.class );
    }

    @Override
    @Transactional
    public UserDto findByRole( final Role role ) {
        if ( role == null ) {
            logger.error( "Error while searching user by role: " + role );
            throw new IllegalArgumentException( "Role cannot be null" );
        }

        return userModelMapperWrapper.getModelMapper().map( userDao.findOneByRole( role ), UserDto.class );
    }

    @Override
    @Transactional
    public List< UserDto > findAll() {
        List< UserDto > userDtoList = new ArrayList<>();
        Iterable< User > users = userDao.findAll();
        if ( users == null ) {
            logger.error( "There are no users in databse" );
            throw new EntityNotFoundException( "There are no users in database" );
        }

        for ( final User user : users ) {
            UserDto userDto = userModelMapperWrapper.getModelMapper().map( user, UserDto.class );
            userDtoList.add( userDto );
        }

        return userDtoList;
    }

    @Override
    @Transactional
    public void removeUserById( final Long id ) {
        if ( LongUtils.isEmpty( id ) ) {
            logger.error( "Error while removing user by id: " + id );
            throw new IllegalArgumentException( "Id cannot be null" );
        }

        userDao.delete( id );
        logger.info( "User: " + id + " removed" );
    }

    @Override
    @Transactional
    public void removeUserByEntity( final UserDto userDto ) {
        if ( !userDao.exists( userDto.getId() ) ) {
            logger.error( "User with id: " + userDto.getId() + " does not exist" );
            throw new EntityNotFoundException( "User with id: " + userDto.getId() + " does not exist" );
        }

        User user = userDao.findOne( userDto.getId() );
        userDao.delete( user );
        logger.info( "User: " + userDto.getId() + " removed" );
    }

    @Override
    @Transactional
    public void changePassword( final PasswordDto passwordDto ) {
        User user = userDao.findOne( passwordDto.getUserId() );
        String currentPassword = user.getPassword();

        if ( !passwordUtils.matches( passwordDto.getOldPassword(), currentPassword ) ) {
            throw new InvalidPasswordException( "Given password is not equals to the current user password" );
        }

        String newPassword = passwordDto.getNewPassword();
        validateUserPassword( newPassword );
        user.setPassword( hashUserPassword( newPassword ) );

        UserDto userDto = userModelMapperWrapper.getModelMapper().map( userDao.save( user ), UserDto.class );

        MapUtils.fillMapWithParams( "email", userDto.getEmail() );
        MapUtils.fillMapWithParams( "username", userDto.getUsername() );
        mailService.sendMessage( MapUtils.getMap(), MailTemplateEnum.USER_PASSWORD_UPDATE );
    }

    @Override
    @Transactional
    public void resetPassword( final String email ) {
        if ( StringUtils.isEmpty( email ) ) {
            logger.error( "Email cannot be null or empty" );
            throw new IllegalArgumentException( "Email cannot be null or empty" );
        }

        User user = userDao.findOneByEmail( email );
        String generatedPassword = passwordUtils.generateRandomPassword();
        user.setPassword( hashUserPassword( generatedPassword ) );

        UserDto userDto = userModelMapperWrapper.getModelMapper().map( userDao.save( user ), UserDto.class );
        userDto.setPassword( generatedPassword );

        MapUtils.fillMapWithParams( "email", userDto.getEmail() );
        MapUtils.fillMapWithParams( "username", userDto.getUsername() );
        MapUtils.fillMapWithParams( "password", userDto.getPassword() );
        mailService.sendMessage( MapUtils.getMap(), MailTemplateEnum.USER_PASSWORD_RESET );
    }

    private void validateUserPassword( final String userPassword ) {
        passwordUtils.validatePassword( userPassword );
    }

    private String hashUserPassword( final String userPassword ) {
        return passwordUtils.encodePasswordWithBCryptPasswordEncoder( userPassword );
    }
}
