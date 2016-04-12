package clock.wise.service.impl;

import clock.wise.converter.UserModelConverter;
import clock.wise.dao.UserDao;
import clock.wise.dtos.UserDto;
import clock.wise.model.User;
import clock.wise.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService
{
    private ModelMapper modelMapper = new ModelMapper();

    {
        modelMapper.addConverter(new UserModelConverter());
    }
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public UserDto addUser( UserDto userDto )
    {
        User user = modelMapper.map(userDto, User.class);
        return modelMapper.map(userDao.save(user), UserDto.class);
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
