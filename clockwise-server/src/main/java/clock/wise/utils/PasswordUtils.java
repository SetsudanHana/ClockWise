package clock.wise.utils;

import clock.wise.exceptions.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PasswordUtils
{
    private static final int PASSWORD_LENGTH = 8;
    private static final String PASSWORD_CHARACTERS = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private static final char[] PASSWORD_GENERATOR_CHARS = "ABCDEFGHIJKLMNOPRSTQWXYZabcdefghijklmnoprstqwxyz1234567890!@#$%^&*()/".toCharArray();

    @Autowired
    private PasswordEncoder encoder;

    public void validatePassword( final String password )
    {
        if ( password == null || password.isEmpty() || password.length() < PASSWORD_LENGTH )
        {
            throw new InvalidPasswordException( "Password is invalid. Password cannot be null, empty and shorter than 8 characters" );
        }
        if ( password.matches( "\\s" ) )
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
        return encoder.encode( password );
    }

    public boolean matches( final String givenPassword, final String currentPassword )
    {
        return encoder.matches( givenPassword, currentPassword );
    }

    public String generateRandomPassword()
    {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for ( int i = 0; i < PASSWORD_LENGTH; ++i )
        {
            char c = PASSWORD_GENERATOR_CHARS[ random.nextInt( PASSWORD_GENERATOR_CHARS.length ) ];
            stringBuilder.append( c );
        }

        return stringBuilder.toString();
    }
}
