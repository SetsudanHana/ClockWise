package clock.wise.converter;

import clock.wise.dtos.UserDto;
import clock.wise.model.User;
import org.modelmapper.AbstractConverter;

public class UserModelConverter extends AbstractConverter<User, UserDto> {

    @Override
    protected UserDto convert(User source) {
        UserDto userDto = new UserDto();
        userDto.email = source.getEmail();
        userDto.id = source.getId();
        userDto.role = source.getRole();
        userDto.username = source.getUsername();
        userDto.password = "";
        return userDto;
    }

}
