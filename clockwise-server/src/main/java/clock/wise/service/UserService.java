package clock.wise.service;

import clock.wise.dto.PasswordDto;
import clock.wise.dto.UserDto;
import clock.wise.model.User;
import clock.wise.model.roles.Role;

import java.util.List;

public interface UserService {
    UserDto createOrUpdate( final UserDto userDto );

    UserDto findById( Long id );

    UserDto findByUsername( String username );

    UserDto findByRole( Role role );

    List< UserDto > findAll();

    void changePassword( final PasswordDto passwordDto );

    void resetPassword( final String email );

    void removeUserById( final Long id );

    void removeUserByEntity( final UserDto userDto );

    void createActivationLinkAndSendMailToUser( final User saved );
}