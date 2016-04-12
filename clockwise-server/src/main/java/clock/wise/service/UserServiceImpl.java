package clock.wise.service;

import clock.wise.dao.UserDao;
import clock.wise.dtos.UserDto;
import clock.wise.model.User;
import clock.wise.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User getUser(long id) {
        return userDao.findOne(id);
    }

    @Override
    public User updateUser(UserDto userDto) {
        User user = userDao.findOne(userDto.id);
        if (user != null) {
            user.setEmail(userDto.email);
            user.setRole(userDto.role);
            user.setPassword(encoder.encode(userDto.password));
            return userDao.save(user);
        }
        return null;
    }

    @Override
    public User removeUser(long id) {
        User user = userDao.findOne(id);
        if (user != null) {
            userDao.delete(user);
            return user;
        }
        return null;
    }

    @Override
    public User createUser(UserDto userDto) {
        return new User(userDto.username, encoder.encode(userDto.password), userDto.email, userDto.role);
    }

}
