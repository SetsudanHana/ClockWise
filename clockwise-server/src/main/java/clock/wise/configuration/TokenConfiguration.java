package clock.wise.configuration;

import clock.wise.configuration.support.AuthenticationTokenProcessingFilter;
import clock.wise.configuration.support.TokenAuthenticationEntryPoint;
import clock.wise.configuration.support.TokenUtilsImpl;
import clock.wise.configuration.support.interfaces.TokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfiguration {

    @Bean
    public TokenAuthenticationEntryPoint getTokenAuthenticationEntryPoint() {
        return new TokenAuthenticationEntryPoint();
    }

    @Bean
    public TokenUtils getTokenUtils() {
        return new TokenUtilsImpl();
    }

    @Bean
    public AuthenticationTokenProcessingFilter getAuthenticationTokenProcessingFilter() {
        return new AuthenticationTokenProcessingFilter();
    }

}
