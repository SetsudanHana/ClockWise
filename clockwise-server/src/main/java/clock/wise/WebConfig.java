package clock.wise;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Gniewko on 2016-04-12.
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void configureContentNegotiation( ContentNegotiationConfigurer configurer ) {
        configurer.favorPathExtension( false ).
                favorParameter( true ).
                parameterName( "mediaType" ).
                ignoreAcceptHeader( true ).
                useJaf( false ).
                mediaType( "xml", MediaType.APPLICATION_XML ).
                mediaType( "json", MediaType.APPLICATION_JSON ).
                mediaType( "jpeg", MediaType.IMAGE_JPEG );
    }
}
