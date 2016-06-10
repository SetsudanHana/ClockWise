package clock.wise.utils;

import clock.wise.dto.CompanyDto;
import clock.wise.model.Company;
import org.apache.commons.lang.StringUtils;

public class CompanyUtils {

    public static void copyDtoToModel( final CompanyDto source, final Company destination ) {
        if ( StringUtils.isNotEmpty( source.getEmail() ) ) {
            destination.setEmail( source.getEmail() );
        }
        if ( StringUtils.isNotEmpty( source.getName() ) ) {
            destination.setName( source.getName() );
        }
    }
}
