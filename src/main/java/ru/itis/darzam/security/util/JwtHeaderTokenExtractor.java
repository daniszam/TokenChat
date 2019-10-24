package ru.itis.darzam.security.util;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.stereotype.Component;
import ru.itis.darzam.security.authentication.JWTAuthentication;
import ru.itis.darzam.security.config.SecurityConfig;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {

    private static String HEADER_PREFIX = "Bearer ";


    @Override
    public Authentication extract(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(SecurityConfig.JWT_TOKEN_HEADER_PARAM);

        if (Strings.isBlank(token)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }
        if (token.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        token = token.substring(HEADER_PREFIX.length(), token.length());
        JWTAuthentication jwtAuthentication = new JWTAuthentication();
        jwtAuthentication.setJwtToken(token);
        return jwtAuthentication;
    }
}