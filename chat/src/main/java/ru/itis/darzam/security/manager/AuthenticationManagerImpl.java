package ru.itis.darzam.security.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.validation.NoProviderFoundException;
import java.util.List;

@Component
public class AuthenticationManagerImpl implements AuthenticationManager {

  private final List<AuthenticationProvider> authenticationProviders;

  public AuthenticationManagerImpl(List<AuthenticationProvider> authenticationProviders) {
    this.authenticationProviders = authenticationProviders;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    AuthenticationProvider provider = authenticationProviders.stream()
            .filter(authenticationProvider -> authenticationProvider.supports(authentication.getClass()))
            .findFirst()
            .orElseThrow(NoProviderFoundException::new);

    return provider.authenticate(authentication);
  }
}
