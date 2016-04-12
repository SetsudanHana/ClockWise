package clock.wise.service.impl;

import clock.wise.converter.UserModelConverter;
import clock.wise.dao.UserDao;
import clock.wise.dtos.UserDto;
import clock.wise.model.User;
import clock.wise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserModelConverter userModelConverter;
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public UserDto addUser( UserDto userDto )
    {
        User user = userModelConverter.convertDtoToEntity( userDto );
        return userModelConverter.convert( userDao.save( user ) );
    }

    @Override
    @Transactional
    public UserDto findByUsername( String username ) throws UsernameNotFoundException
    {
        UserDto userDto = userModelConverter.convert( userDao.findOneByUsername( username ) );
        if ( userDto == null )
        {
            throw new UsernameNotFoundException( "Username: " + username + " not found" );
        }
        return userDto;
    }

    @Override
    public UserDto getUser( long id )
    {
        return null;
    }

    @Override
    public UserDto updateUser( UserDto userDto )
    {
        return null;
    }

    @Override
    public UserDto removeUser( long id )
    {
        return null;
    }

}
