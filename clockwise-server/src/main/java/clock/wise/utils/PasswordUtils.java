package clock.wise.utils;

import clock.wise.exceptions.InvalidPasswordException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtils
{
    private static final int PASSWORD_LENGTH = 8;
    private static final int STRENGTH_LOG_ROUNDS = 6;
    private static final String PASSWORD_CHARACTERS = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private PasswordEncoder encoder;

    public void validatePassword( final String password )
    {
        if ( password == null || password.isEmpty() || password.length() < PASSWORD_LENGTH )
        {
            throw new InvalidPasswordException( "Password is invalid. Password cannot be null, empty or shorter than 8 characters" );
        }
        if ( password.contains( " " ) )
        {
            throw new InvalidPasswordException( "Password cannot contains whitespaces" );
        }
        if ( !password.matches( PASSWORD_CHARACTERS ) )
        {
            throw new InvalidPasswordException( "Password should contains special characters: a lower case, an upper case, a digit, special character." );
        }
    }

    public String encodePasswordWithBCryptPasswordEncoder( final String password )
    {
        encoder = new BCryptPasswordEncoder( STRENGTH_LOG_ROUNDS );
        return encoder.encode( password );
    }
}
