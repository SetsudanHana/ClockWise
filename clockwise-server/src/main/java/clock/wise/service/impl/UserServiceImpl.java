package clock.wise.service.impl;

import clock.wise.dao.UserDao;
import clock.wise.dto.UserDto;
import clock.wise.model.User;
import clock.wise.model.roles.Role;
import clock.wise.service.UserService;
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

    @Override
    @Transactional
    public UserDto createOrUpdateUser( final UserDto userDto )
    {
        User user = UserModelMapperUtils.modelMapper.map( userDto, User.class );
        logger.info( "User " + user.getUsername() + " has been created" );

        return UserModelMapperUtils.modelMapper.map( userDao.save( user ), UserDto.class );
    }

    @Override
    @Transactional
    public UserDto findById( final Long id )
    {
        return UserModelMapperUtils.modelMapper.map( userDao.findOne( id ), UserDto.class );
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

        return UserModelMapperUtils.modelMapper.map( userDao.findOneByUsername( username ), UserDto.class );
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

        return UserModelMapperUtils.modelMapper.map( userDao.findOneByRole( role ), UserDto.class );
    }

    @Override
    @Transactional
    public List< UserDto > findAll()
    {
        List<UserDto> userDtoList = new ArrayList<UserDto>();

        Iterable< User > users = userDao.findAll();
        for ( User user : users )
        {
            UserDto userDto = UserModelMapperUtils.modelMapper.map( user, UserDto.class );
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
}
