package clock.wise.controller;

import clock.wise.dto.MessageDto;
import clock.wise.dto.PasswordDto;
import clock.wise.dto.UserDto;
import clock.wise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/users" )
public class UserController
{
    @Autowired
    private UserService userService;

    @RequestMapping( method = RequestMethod.POST )
    @ResponseStatus( HttpStatus.CREATED )
    public UserDto createUser( @RequestBody final UserDto userDto )
    {
        return userService.createOrUpdateUser( userDto );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public UserDto findById( @PathVariable( "id" ) final Long id )
    {
        return userService.findById( id );
    }

    @RequestMapping( method = RequestMethod.GET )
    public UserDto findByUsername( @RequestParam( value = "username" ) final String username )
    {
        return userService.findByUsername( username );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.PUT )
    public UserDto updateUser( @PathVariable( "id" ) final Long id, @RequestBody final UserDto userDto )
    {
        userDto.setId( id );
        return userService.createOrUpdateUser( userDto );
    }

    @RequestMapping( value = "/{id}/update_password", method = RequestMethod.PATCH )
    public MessageDto changeUserPassword( @PathVariable( "id" ) final Long id, @RequestBody final PasswordDto passwordDto )
    {
        passwordDto.setUserId( id );
        userService.changePassword( passwordDto );

        return new MessageDto( "Password has been changed for user id: " + id );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void removeUser( @PathVariable( "id" ) final Long id )
    {
        userService.removeUser( id );
    }
}
