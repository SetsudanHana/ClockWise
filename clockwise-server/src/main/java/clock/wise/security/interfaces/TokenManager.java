package clock.wise.security.interfaces;

import clock.wise.security.model.Token;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenManager {

    Token getToken( UserDetails userDetails );

    Token getToken( UserDetails userDetails, Long expiration );

    boolean validate( Token token );

    UserDetails getUserFromToken( Token token );

    void invalidateToken( UserDetails userDetails );

    void invalidateToken( Token token );
}
