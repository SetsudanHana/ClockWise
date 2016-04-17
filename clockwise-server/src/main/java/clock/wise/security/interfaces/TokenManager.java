package clock.wise.security.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenManager {

    String getToken(UserDetails userDetails);

    String getToken(UserDetails userDetails, Long expiration);

    boolean validate(String token);

    UserDetails getUserFromToken(String token);

    void invalidateToken(UserDetails userDetails);
}
