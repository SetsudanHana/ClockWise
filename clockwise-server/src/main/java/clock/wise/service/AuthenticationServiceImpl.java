package clock.wise.service;

import clock.wise.configuration.support.interfaces.TokenUtils;
import clock.wise.dao.UserDao;
import clock.wise.dtos.TokenDto;
import clock.wise.dtos.UserFormDto;
import clock.wise.model.User;
import clock.wise.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserDao userDao;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public TokenDto authenticate(UserFormDto userFormDto) {
        User user = userDao.findOneByUsername(userFormDto.username);
        if (user != null) {
            if (passwordEncoder.matches(userFormDto.password, user.getPassword())) {
                TokenDto tokenDto = new TokenDto();
                tokenDto.token = tokenUtils.getToken(user);
                return tokenDto;
            }
            throw new BadCredentialsException("Invalid password for username: " + userFormDto.username);
        }
        throw new UsernameNotFoundException("Username: " + userFormDto.username + " not found");
    }

    @Override
    public void invalidateToken(UserDetails userDetails) {
        tokenUtils.invalidateToken(userDetails);
    }

}
