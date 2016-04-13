package clock.wise.converter;

import clock.wise.dto.UserDto;
import clock.wise.model.User;
import org.modelmapper.AbstractConverter;

public class UserModelConverter extends AbstractConverter< User, UserDto >
{
    @Override
    public UserDto convert( User source )
    {
        UserDto userDto = new UserDto();
        userDto.setEmail( source.getEmail() );
        userDto.setId( source.getId() );
        userDto.setRole( source.getRole() );
        userDto.setUsername( source.getUsername() );
        userDto.setPassword( source.getPassword() );

        return userDto;
    }
}
