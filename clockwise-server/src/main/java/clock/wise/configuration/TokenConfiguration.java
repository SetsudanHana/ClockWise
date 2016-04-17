package clock.wise.configuration;

import clock.wise.security.AuthenticationTokenProcessingFilter;
import clock.wise.security.TokenAuthenticationEntryPoint;
import clock.wise.security.TokenManagerImpl;
import clock.wise.security.interfaces.TokenManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfiguration {

    @Bean
    public TokenAuthenticationEntryPoint getTokenAuthenticationEntryPoint() {
        return new TokenAuthenticationEntryPoint();
    }

    @Bean
    public TokenManager getTokenUtils() {
        return new TokenManagerImpl();
    }

    @Bean
    public AuthenticationTokenProcessingFilter getAuthenticationTokenProcessingFilter() {
        return new AuthenticationTokenProcessingFilter();
    }

}
