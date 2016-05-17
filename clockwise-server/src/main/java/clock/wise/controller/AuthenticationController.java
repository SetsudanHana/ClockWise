package clock.wise.controller;

import clock.wise.dto.TokenDto;
import clock.wise.dto.UserFormDto;
import clock.wise.model.User;
import clock.wise.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api" )
public class AuthenticationController
{
    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping( value = "/authenticate", method = RequestMethod.POST )
    public TokenDto authenticate( @RequestBody UserFormDto userFormDto )
    {
        return authenticationService.authenticate( userFormDto );
    }

    @RequestMapping( value = "/invalidatetoken", method = RequestMethod.POST )
    public void invalidateToken()
    {
        authenticationService.invalidateToken((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
