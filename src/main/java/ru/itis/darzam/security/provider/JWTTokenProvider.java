package ru.itis.darzam.security.provider;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JWTTokenProvider implements AuthenticationProvider {

  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    authentication.setAuthenticated(false);
    return authentication;
  }

  public boolean supports(Class<?> aClass) {
    return true;
  }
}
