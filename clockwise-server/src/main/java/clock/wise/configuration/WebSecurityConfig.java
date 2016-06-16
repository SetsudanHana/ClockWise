package clock.wise.configuration;

import clock.wise.dao.CompanyDao;
import clock.wise.dao.UserDao;
import clock.wise.enums.UserStatus;
import clock.wise.model.Company;
import clock.wise.model.User;
import clock.wise.model.roles.Role;
import clock.wise.security.AuthenticationTokenProcessingFilter;
import clock.wise.security.TokenAuthenticationEntryPoint;
import clock.wise.security.password.BasicPasswordEncoder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger log = Logger.getLogger( WebSecurityConfig.class );

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint;

    @Autowired
    AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter;

    @Autowired
    UserDao userDao;

    @Autowired
    CompanyDao companyDao;

    @Value( "${default.superadmin.login}" )
    private String superUserUsername;
    @Value( "${default.superadmin.password}" )
    private String superUserPassword;
    @Value( "${default.superadmin.email}" )
    private String superUserEmail;

    private PasswordEncoder encoder = new BasicPasswordEncoder();

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy( SessionCreationPolicy.STATELESS );

        http.exceptionHandling()
                .authenticationEntryPoint( tokenAuthenticationEntryPoint );

        http.authorizeRequests()
                .antMatchers( "/api/authenticate" ).permitAll()
                .antMatchers( "/api/users" ).permitAll()
                .antMatchers( "/api/companies").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .disable();

        http.logout()
                .disable();

        http.addFilterBefore( authenticationTokenProcessingFilter, UsernamePasswordAuthenticationFilter.class );

        http.csrf()
                .disable();

    }

    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService( userDetailsService() ).passwordEncoder( encoder );
        checkAndCreateSuperAdmin();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return encoder;
    }

    private void checkAndCreateSuperAdmin() {
        if ( userDao.findOneByRole( Role.ROLE_SUPER_ADMIN ) == null ) {
            Company company = new Company();
            company.setName( "Default" );
            company = companyDao.save( company );

            log.info( "Creating SUPER ADMIN user" );
            User superAdmin = new User( superUserUsername,
                    encoder.encode( superUserPassword ),
                    superUserEmail,
                    Role.ROLE_SUPER_ADMIN );
            superAdmin.setCompany( company );
            superAdmin.setStatus( UserStatus.ACTIVE );
            userDao.save( superAdmin );
        }
    }
}
