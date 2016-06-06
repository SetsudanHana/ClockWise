package clock.wise.service;

import clock.wise.dto.ActivationLinkDto;

public interface ActivationLinkService {
    ActivationLinkDto createLinkForCompany( final Long id );

    ActivationLinkDto activateCompanyByLink( final String hash );
}
