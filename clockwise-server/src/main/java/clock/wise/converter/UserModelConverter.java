package clock.wise.converter;

import clock.wise.dto.UserDto;
import clock.wise.model.ActivationLink;
import clock.wise.model.User;
import org.modelmapper.AbstractConverter;

public class UserModelConverter extends AbstractConverter< User, UserDto > {

    @Override
    protected UserDto convert( final User user ) {
        UserDto userDto = new UserDto();
        userDto.setId( user.getId() );
        userDto.setUsername( user.getUsername() );
        userDto.setPassword( "" );
        userDto.setEmail( user.getEmail() );
        userDto.setRole( user.getRole() );
        userDto.setCompanyId( user.getCompany().getId() );
        userDto.setStatus( user.getStatus() );

        ActivationLink activationLink = user.getActivationLink();
        if(activationLink != null) {
            userDto.setActivationLinkId( activationLink.getId() );
        }

        return userDto;
    }
}
