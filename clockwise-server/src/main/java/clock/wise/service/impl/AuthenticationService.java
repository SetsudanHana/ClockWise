package clock.wise.service.impl;

import clock.wise.dtos.TokenDto;
import clock.wise.dtos.UserFormDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {

    TokenDto authenticate(UserFormDto userFormDto);

    void invalidateToken(UserDetails userDetails);

}
