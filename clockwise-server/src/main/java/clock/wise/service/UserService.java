package clock.wise.service;

import clock.wise.dto.UserDto;
import clock.wise.model.roles.Role;

public interface UserService
{
    UserDto createOrUpdateUser( final UserDto userDto );

    UserDto findById( Long id );

    UserDto findByUsername( String username );

    UserDto findByRole( Role role );

    void removeUser( Long id );
}
