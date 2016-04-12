package clock.wise.controller;

import clock.wise.dtos.UserDto;
import clock.wise.service.UserService;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/users" )
public class UserController extends AbstractController
{
    @Autowired
    private UserService userService;

    @RequestMapping( method = RequestMethod.POST )
    public UserDto addUser( @RequestBody final UserDto userDto ) throws MessagingException
    {
        return userService.addUser( userDto );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void deleteUser( @PathVariable( "id" ) final Long id )
    {

    }
}
