package clock.wise.security.converter;

import clock.wise.model.User;
import clock.wise.security.model.UserForm;
import org.modelmapper.AbstractConverter;

public class UserToUserFormConverter extends AbstractConverter<User, UserForm> {

    @Override
    protected UserForm convert(User source) {
        return new UserForm(source.getUsername(), "");
    }

}
