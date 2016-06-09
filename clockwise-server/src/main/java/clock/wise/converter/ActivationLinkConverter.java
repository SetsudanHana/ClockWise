package clock.wise.converter;

import clock.wise.dto.ActivationLinkDto;
import clock.wise.model.ActivationLink;
import clock.wise.model.Company;
import clock.wise.model.User;
import org.modelmapper.AbstractConverter;

public class ActivationLinkConverter extends AbstractConverter< ActivationLink, ActivationLinkDto > {
    @Override
    protected ActivationLinkDto convert( final ActivationLink activationLink ) {
        ActivationLinkDto activationLinkDto = new ActivationLinkDto();
        activationLinkDto.setId( activationLink.getId() );
        activationLinkDto.setStatus( activationLink.getStatus() );
        activationLinkDto.setLink( activationLink.getLink() );
        activationLinkDto.setCreatedDate( activationLink.getCreatedDate() );
        Company company = activationLink.getCompany();
        if ( company != null ) {
            activationLinkDto.setCompanyId( company.getId() );
        }
        User user = activationLink.getUser();
        if ( user != null ) {
            activationLinkDto.setUserId( user.getId() );
        }

        return activationLinkDto;
    }
}