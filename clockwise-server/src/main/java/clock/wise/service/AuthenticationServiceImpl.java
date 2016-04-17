package clock.wise.service;

import clock.wise.dao.UserDao;
import clock.wise.dto.TokenDto;
import clock.wise.dto.UserFormDto;
import clock.wise.model.User;
import clock.wise.security.interfaces.TokenManager;
import clock.wise.security.mapper.UserFormModelMapperWrapper;
import clock.wise.security.model.UserForm;
import clock.wise.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserDao userDao;

    @Autowired
    TokenManager tokenManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserFormModelMapperWrapper modelMapperWrapper;

    @Override
    public TokenDto authenticate(UserFormDto userFormDto) {
        UserForm userForm = modelMapperWrapper.getModelMapper().map(userFormDto, UserForm.class);
        User user = userDao.findOneByUsername(userForm.getUsername());
        if (user != null) {
            if (passwordEncoder.matches(userForm.getPassword(), user.getPassword())) {
                TokenDto tokenDto = new TokenDto();
                tokenDto.token = tokenManager.getToken(userForm);
                return tokenDto;
            }
            throw new BadCredentialsException("Invalid password for username: " + userFormDto.username);
        }
        throw new UsernameNotFoundException("Username: " + userFormDto.username + " not found");
    }

    @Override
    public void invalidateToken(User user) {
        tokenManager.invalidateToken(modelMapperWrapper.getModelMapper().map(user, UserForm.class));
    }

}
