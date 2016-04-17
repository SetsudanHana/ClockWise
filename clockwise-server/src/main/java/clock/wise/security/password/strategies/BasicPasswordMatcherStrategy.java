package clock.wise.security.password.strategies;

import clock.wise.security.utils.SHA1Utils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BasicPasswordMatcherStrategy extends SimplePasswordMatcherStrategy {
    public BasicPasswordMatcherStrategy(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String preHashedPassword = SHA1Utils.getInstance().encode(rawPassword.toString());
        return super.matches(preHashedPassword, encodedPassword);
    }
}
