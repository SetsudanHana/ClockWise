package clock.wise.service;

import clock.wise.dtos.UserDto;

/**
 * Created by Gniewko on 2016-04-12.
 */
public interface UserService
{
    UserDto addUser(final UserDto userDto);

    UserDto getUser( long id);

    UserDto updateUser(UserDto userDto);

    UserDto removeUser(long id);
}
