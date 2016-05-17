package clock.wise.service;

import clock.wise.dto.TokenDto;
import clock.wise.dto.UserFormDto;
import clock.wise.model.User;

public interface AuthenticationService {

    TokenDto authenticate(UserFormDto userFormDto);

    void invalidateToken(User username);

}
