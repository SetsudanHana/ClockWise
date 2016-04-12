package clock.wise.service.interfaces;

import clock.wise.dtos.UserDto;
import clock.wise.model.User;

public interface UserService {

    User getUser(long id);

    User updateUser(UserDto userDto);

    User removeUser(long id);

    User createUser(UserDto userDto);

}
