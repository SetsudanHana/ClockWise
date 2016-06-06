package clock.wise.converter;

import clock.wise.dto.ActivationLinkDto;
import clock.wise.model.ActivationLink;
import org.modelmapper.AbstractConverter;

public class ActivationLinkConverter extends AbstractConverter< ActivationLink, ActivationLinkDto > {
    @Override
    protected ActivationLinkDto convert( final ActivationLink activationLink ) {
        ActivationLinkDto activationLinkDto = new ActivationLinkDto();
        activationLinkDto.setId( activationLink.getId() );
        activationLinkDto.setStatus( activationLink.getStatus() );
        activationLinkDto.setLink( activationLink.getLink() );
        activationLinkDto.setCreatedDate( activationLink.getCreatedDate() );
        activationLinkDto.setCompanyId( activationLink.getCompany().getId() );

        return activationLinkDto;
    }
}