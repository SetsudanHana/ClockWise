package clock.wise.security.password;

import clock.wise.security.interfaces.PasswordMatcherStrategy;
import clock.wise.security.password.strategies.BasicPasswordMatcherStrategy;
import clock.wise.security.password.strategies.SimplePasswordMatcherStrategy;
import clock.wise.security.utils.SHA1Utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class BasicPasswordEncoder implements PasswordEncoder {

    private final static int SECURITY_STRENGTH = 6;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(SECURITY_STRENGTH);

    private List<PasswordMatcherStrategy> passwordMatcherStrategies = new ArrayList<>();

    {
        passwordMatcherStrategies.add(new SimplePasswordMatcherStrategy(encoder));
        passwordMatcherStrategies.add(new BasicPasswordMatcherStrategy(encoder));
    }

    public BasicPasswordEncoder() {
    }

    @Override
    public String encode(CharSequence rawPassword) {
        String preHashedPassword = SHA1Utils.getInstance().encode(rawPassword.toString());
        return encoder.encode(preHashedPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        for (PasswordMatcherStrategy strategy : passwordMatcherStrategies) {
            if (strategy.matches(rawPassword, encodedPassword)) {
                return true;
            }
        }
        return false;
    }

}
