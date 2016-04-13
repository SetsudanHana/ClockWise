package clock.wise.configuration.support.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenUtils {

    String getToken(UserDetails userDetails);

    String getToken(UserDetails userDetails, Long expiration);

    boolean validate(String token);

    UserDetails getUserFromToken(String token);

    void invalidateToken(String token);
}
