package clock.wise.security;


import clock.wise.dto.TokenDto;
import clock.wise.security.interfaces.TokenManager;
import clock.wise.security.mapper.TokenModelMapperWrapper;
import clock.wise.security.model.Token;
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

    @Autowired
    TokenModelMapperWrapper tokenModelMapperWrapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String token = null;

        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            token = httpServletRequest.getHeader("ClockWise-Token");
        }

        if (token != null) {
            TokenDto tokenDto = new TokenDto();
            tokenDto.token = token;
            Token tokenModel = tokenModelMapperWrapper.getModelMapper().map(tokenDto, Token.class);
            if (tokenManager.validate(tokenModel)) {
                UserDetails userDetails = tokenManager.getUserFromToken(tokenModel);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
                SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authentication));
            } else {
                tokenManager.invalidateToken(tokenModel);
            }
        }
        // continue thru the filter chain
        chain.doFilter(request, response);
    }
}
