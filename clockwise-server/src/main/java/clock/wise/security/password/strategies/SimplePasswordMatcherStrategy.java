package clock.wise.security.password.strategies;

import clock.wise.security.interfaces.PasswordMatcherStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;


public class SimplePasswordMatcherStrategy implements PasswordMatcherStrategy {

    private PasswordEncoder passwordEncoder;

    public SimplePasswordMatcherStrategy(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
