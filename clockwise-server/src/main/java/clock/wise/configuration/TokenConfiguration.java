package clock.wise.configuration;

import clock.wise.configuration.support.TokenAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfiguration {

    @Bean
    public TokenAuthenticationEntryPoint getTokenAuthenticationEntryPoint() {
        return new TokenAuthenticationEntryPoint();
    }


}
