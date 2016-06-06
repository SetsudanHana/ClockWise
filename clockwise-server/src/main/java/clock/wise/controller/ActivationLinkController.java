package clock.wise.controller;

import clock.wise.dto.ActivationLinkDto;
import clock.wise.service.ActivationLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/links" )
public class ActivationLinkController {
    @Autowired
    private ActivationLinkService linkService;

    @RequestMapping( value = "/{hash}/companies", method = RequestMethod.GET )
    public ActivationLinkDto activateCompanyLink( @PathVariable( "hash" ) final String hash ) {
        return linkService.activateCompanyByLink( hash );
    }

    @RequestMapping( value = "/{hash}/users", method = RequestMethod.GET )
    public ActivationLinkDto activateUserLink( @PathVariable( "hash" ) final String hash ) {
        //FIXME - user activation
        return linkService.activateCompanyByLink( hash );
    }
}