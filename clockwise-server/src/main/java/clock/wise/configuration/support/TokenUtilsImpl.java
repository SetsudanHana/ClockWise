package clock.wise.configuration.support;

import clock.wise.configuration.support.interfaces.TokenUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenUtilsImpl implements TokenUtils {

    @Override
    public String getToken(UserDetails userDetails) {
        return null;
    }

    @Override
    public String getToken(UserDetails userDetails, Long expiration) {
        return null;
    }

    @Override
    public boolean validate(String token) {
        return false;
    }

    @Override
    public UserDetails getUserFromToken(String token) {
        return null;
    }

    @Override
    public void invalidateToken(String token) {

    }

}
