package clock.wise.configuration;

import clock.wise.dao.UserDao;
import clock.wise.model.User;
import clock.wise.model.roles.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger log = Logger.getLogger(WebSecurityConfig.class);

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserDao userDao;

    @Value("${default.superadmin.login}")
    private String superUserUsername;
    @Value("${default.superadmin.password}")
    private String superUserPassword;
    @Value("${default.superadmin.email}")
    private String superUserEmail;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .clearAuthentication(true)
                .permitAll()
                .and()
                .csrf()
                .disable();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(encoder);
        checkAndCreateSuperAdmin();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    private void checkAndCreateSuperAdmin() {
        if (userDao.findOneByRole(Role.ROLE_SUPER_ADMIN) == null) {
            log.info("Creating SUPER ADMIN user");
            User superAdmin = new User(superUserUsername,
                    encoder.encode(superUserPassword),
                    superUserEmail,
                    Role.ROLE_SUPER_ADMIN);
            userDao.save(superAdmin);
        }
    }
}
