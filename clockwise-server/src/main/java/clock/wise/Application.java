package clock.wise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootApplication
@ComponentScan
@EnableGlobalMethodSecurity( securedEnabled = true, prePostEnabled = true )
public class Application {

    private static final Logger log = LoggerFactory.getLogger( Application.class );

    public static void main( String[] args ) {
        ApplicationContext ctx = SpringApplication.run( Application.class, args );
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding( "UTF-8" );
        characterEncodingFilter.setForceEncoding( true );
        return characterEncodingFilter;
    }

}
