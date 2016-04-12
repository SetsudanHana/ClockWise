package clock.wise.service;

import clock.wise.dtos.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by Gniewko on 2016-04-12.
 */
public interface UserService
{
    UserDto addUser(final UserDto userDto);

    UserDto findByUsername(final String username) throws UsernameNotFoundException;
}
