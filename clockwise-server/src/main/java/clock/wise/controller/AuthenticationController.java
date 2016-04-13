package clock.wise.controller;

import clock.wise.dtos.TokenDto;
import clock.wise.dtos.UserFormDto;
import clock.wise.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController extends AbstractController {

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public TokenDto authenticate(@RequestBody UserFormDto userFormDto) {
        return authenticationService.authenticate(userFormDto);
    }

    @RequestMapping(value = "/invalidatetoken", method = RequestMethod.POST)
    public void invalidateToken() {
        authenticationService.invalidateToken((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
