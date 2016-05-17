package clock.wise.converter;

import clock.wise.dto.UserDto;
import clock.wise.model.User;
import org.modelmapper.AbstractConverter;

public class UserModelConverter extends AbstractConverter< User, UserDto >
{

    @Override
    protected UserDto convert( final User source )
    {
        UserDto userDto = new UserDto();
        userDto.setId( source.getId() );
        userDto.setUsername( source.getUsername() );
        userDto.setEmail( source.getEmail() );
        userDto.setRole( source.getRole() );
        userDto.setPassword( "" );
        userDto.setCompanyId(source.getCompany().getId());

        return userDto;
    }
}
