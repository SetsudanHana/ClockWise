package clock.wise.security.interfaces;

public interface PasswordMatcherStrategy {

    boolean matches(CharSequence rawPassword, String encodedPassword);

}
