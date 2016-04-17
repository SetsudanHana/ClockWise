package clock.wise.security.converter;

import clock.wise.dto.UserFormDto;
import clock.wise.security.model.UserForm;
import clock.wise.security.utils.SHA1Utils;
import org.modelmapper.AbstractConverter;

public class UserFormDtoConverter extends AbstractConverter<UserFormDto, UserForm> {

    @Override
    protected UserForm convert(UserFormDto source) {
        return new UserForm(source.username, SHA1Utils.getInstance().encode(source.password));
    }

}
