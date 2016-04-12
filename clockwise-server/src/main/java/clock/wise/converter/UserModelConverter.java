package clock.wise.converter;

import clock.wise.dtos.UserDto;
import clock.wise.model.User;
import org.modelmapper.AbstractConverter;

public class UserModelConverter extends AbstractConverter< User, UserDto >
{

    @Override
    public UserDto convert( User source )
    {
        UserDto userDto = new UserDto();
        userDto.email = source.getEmail();
        userDto.id = source.getId();
        userDto.role = source.getRole();
        userDto.username = source.getUsername();
        userDto.password = "";
        return userDto;
    }

    public User convertDtoToEntity( final UserDto userDto )
    {
        User user = new User();
        user.setEmail( userDto.email );
        user.setRole( userDto.role );
        user.setPassword( userDto.password );

        return user;
    }

}
