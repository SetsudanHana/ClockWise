package clock.wise.service;

import clock.wise.dto.PasswordDto;
import clock.wise.dto.UserDto;
import clock.wise.model.roles.Role;

import java.util.List;

public interface UserService
{
    UserDto createOrUpdateUser( final UserDto userDto );

    UserDto findById( Long id );

    UserDto findByUsername( String username );

    UserDto findByRole( Role role );

    List< UserDto > findAll();

    void changePassword( final PasswordDto passwordDto );

    void resetPassword( final String email );

    void removeUser( Long id );
}