package clock.wise.service;

import clock.wise.dto.ActivationLinkDto;
import clock.wise.dto.CompanyDto;
import clock.wise.dto.UserDto;

public interface ActivationLinkService {
    ActivationLinkDto createLinkForCompany( final Long id );

    ActivationLinkDto createLinkForUser( final Long userId );

    CompanyDto activateCompanyByLink( final String hash );

    UserDto activateUserByLink( final String hash );
}
