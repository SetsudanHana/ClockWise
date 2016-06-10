package clock.wise.controller;

import clock.wise.dto.ActivationLinkDto;
import clock.wise.dto.CompanyDto;
import clock.wise.dto.UserDto;
import clock.wise.service.ActivationLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api/links" )
public class ActivationLinkController {
    @Autowired
    private ActivationLinkService linkService;

    @RequestMapping( value = "/{hash}/companies", method = RequestMethod.GET )
    public CompanyDto activateCompanyLink( @PathVariable( "hash" ) final String hash ) {
        return linkService.activateCompanyByLink( hash );
    }

    @RequestMapping( value = "/{hash}/users", method = RequestMethod.GET )
    public UserDto activateUserLink( @PathVariable( "hash" ) final String hash ) {
        return linkService.activateUserByLink( hash );
    }

    @RequestMapping( method = RequestMethod.GET )
    public List< ActivationLinkDto > findAllActivationLinks() {
        return linkService.findAll();
    }
}