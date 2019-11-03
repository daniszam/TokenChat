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

    @Override
    public Authentication extract(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(SecurityConfig.JWT_TOKEN_HEADER_PARAM);

        if (Strings.isBlank(token)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }

        JWTAuthentication jwtAuthentication = new JWTAuthentication();
        jwtAuthentication.setToken(token);
        return jwtAuthentication;
    }
}