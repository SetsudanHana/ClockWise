package clock.wise.service;

import clock.wise.dao.UserDao;
import clock.wise.dto.TokenDto;
import clock.wise.dto.UserFormDto;
import clock.wise.model.User;
import clock.wise.security.interfaces.TokenManager;
import clock.wise.security.mapper.TokenModelMapperWrapper;
import clock.wise.security.mapper.UserFormModelMapperWrapper;
import clock.wise.security.model.Token;
import clock.wise.security.model.UserForm;
import clock.wise.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final long TOKEN_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(30);

    @Autowired
    UserDao userDao;

    @Autowired
    TokenManager tokenManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserFormModelMapperWrapper userFormModelMapperWrapper;

    @Autowired
    TokenModelMapperWrapper tokenModelMapperWrapper;

    @Override
    public TokenDto authenticate(UserFormDto userFormDto) {
        UserForm userForm = userFormModelMapperWrapper.getModelMapper().map(userFormDto, UserForm.class);
        User user = userDao.findOneByUsername(userForm.getUsername());
        if (user != null) {
            if (passwordEncoder.matches(userForm.getPassword(), user.getPassword())) {
                Token token = tokenManager.getToken(userForm, TOKEN_EXPIRATION_TIME);
                return tokenModelMapperWrapper.getModelMapper().map(token, TokenDto.class);
            }
            throw new BadCredentialsException("Invalid password for username: " + userFormDto.username);
        }
        throw new UsernameNotFoundException("Username: " + userFormDto.username + " not found");
    }

    @Override
    public void invalidateToken(User user) {
        tokenManager.invalidateToken(userFormModelMapperWrapper.getModelMapper().map(user, UserForm.class));
    }

}
