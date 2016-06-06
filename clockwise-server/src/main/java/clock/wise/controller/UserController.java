package clock.wise.controller;

import clock.wise.dto.EmailDto;
import clock.wise.dto.PasswordDto;
import clock.wise.dto.StatisticDto;
import clock.wise.dto.UserDto;
import clock.wise.service.StatisticService;
import clock.wise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/users" )
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StatisticService statisticService;

    @RequestMapping( method = RequestMethod.POST )
    @ResponseStatus( HttpStatus.CREATED )
    public UserDto createUser( @RequestBody final UserDto userDto ) {
        return userService.createOrUpdate( userDto );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public UserDto findById( @PathVariable( "id" ) final Long id ) {
        return userService.findById( id );
    }

    @RequestMapping( method = RequestMethod.GET )
    public UserDto findByUsername( @RequestParam( value = "username" ) final String username ) {
        return userService.findByUsername( username );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.PUT )
    public UserDto updateUser( @PathVariable( "id" ) final Long id, @RequestBody final UserDto userDto ) {
        userDto.setId( id );
        return userService.createOrUpdate( userDto );
    }

    @RequestMapping( value = "/{id}/statistics", method = RequestMethod.POST )
    public StatisticDto updateUserStatistics( @PathVariable( "id" ) final Long userId, @RequestBody final StatisticDto statisticDto ) {
        return statisticService.createOrUpdateStatistic( statisticDto, userId );
    }

    @RequestMapping( value = "/{id}/statistics", method = RequestMethod.PUT )
    public List< StatisticDto > updateUserStatistics( @PathVariable( "id" ) final Long userId, @RequestBody final List< StatisticDto > statistics ) {
        return statisticService.createOrUpdateStatistics( statistics, userId );
    }

    @RequestMapping( value = "/{id}/statistics", method = RequestMethod.GET )
    public List< StatisticDto > getUserStatistics( @PathVariable( "id" ) final Long id ) {
        return statisticService.findStatisticsByUserId( id );
    }

    @RequestMapping( value = "/{id}/update_password", method = RequestMethod.PATCH )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void changeUserPassword( @PathVariable( "id" ) final Long id, @RequestBody final PasswordDto passwordDto ) {
        passwordDto.setUserId( id );
        userService.changePassword( passwordDto );
    }

    @RequestMapping( value = "/reset_password", method = RequestMethod.POST )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void resetUserPassword( @RequestBody final EmailDto emailDto ) {
        userService.resetPassword( emailDto.getEmail() );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void removeUserById( @PathVariable( "id" ) final Long id ) {
        userService.removeUserById( id );
    }

    @RequestMapping( method = RequestMethod.DELETE )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void removeUserByEntity( @RequestBody final UserDto userDto ) {
        userService.removeUserByEntity( userDto );
    }
}
