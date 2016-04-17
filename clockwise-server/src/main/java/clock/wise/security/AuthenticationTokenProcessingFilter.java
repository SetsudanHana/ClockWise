package clock.wise.security;


import clock.wise.security.interfaces.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    @Autowired
    TokenManager tokenManager;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String token = null;

        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            token = httpServletRequest.getHeader("ClockWise-Token");
        }

        if (token != null) {
            // validate the token
            if (tokenManager.validate(token)) {
                // determine the user based on the (already validated) token
                UserDetails userDetails = tokenManager.getUserFromToken(token);
                // build an Authentication object with the user's info
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
                // set the authentication into the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authentication));
            }
        }
        // continue thru the filter chain
        chain.doFilter(request, response);
    }
}
