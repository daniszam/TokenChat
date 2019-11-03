package ru.itis.darzam.security.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenExtractor tokenExtractor;

    public JWTFilter(String securityPath, TokenExtractor tokenExtractor) {
        super(securityPath);
        this.tokenExtractor = tokenExtractor;
    }

    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        Authentication authentication = tokenExtractor.extract(httpServletRequest);
        return getAuthenticationManager()
                .authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

//        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
