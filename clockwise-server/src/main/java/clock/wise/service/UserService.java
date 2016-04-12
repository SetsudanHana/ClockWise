package clock.wise.service;

import clock.wise.dtos.UserDto;
import clock.wise.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by Gniewko on 2016-04-12.
 */
public interface UserService
{
    UserDto addUser(final UserDto userDto);

    UserDto findByUsername(final String username) throws UsernameNotFoundException;

    UserDto getUser( long id);

    UserDto updateUser(UserDto userDto);

    UserDto removeUser(long id);
}
