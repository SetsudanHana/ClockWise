package clock.wise.service.impl;

import clock.wise.dtos.TokenDto;
import clock.wise.dtos.UserFormDto;

public interface AuthenticationService {

    TokenDto authenticate(UserFormDto userFormDto);

    void invalidateToken(String username);

}
