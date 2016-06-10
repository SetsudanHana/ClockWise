package clock.wise.utils;

import clock.wise.dto.UserDto;
import clock.wise.model.User;
import org.apache.commons.lang.StringUtils;

public class UserUtils {

    public static void copyDtoToModel( final UserDto source, final User destination ) {
        if ( StringUtils.isNotEmpty( source.getUsername() ) ) {
            destination.setUsername( source.getUsername() );
        }
        if ( StringUtils.isNotEmpty( source.getEmail() ) ) {
            destination.setEmail( source.getEmail() );
        }
        if ( source.getRole() != null ) {
            destination.setRole( source.getRole() );
        }
    }
}
