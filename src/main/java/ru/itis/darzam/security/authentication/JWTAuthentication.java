package ru.itis.darzam.security.authentication;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthentication implements Authentication {

  private String jwtToken;

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  public Object getCredentials() {
    return null;
  }

  public Object getDetails() {
    return null;
  }

  public Object getPrincipal() {
    return null;
  }

  public boolean isAuthenticated() {
    return false;
  }

  public void setAuthenticated(boolean b) throws IllegalArgumentException {

  }

  public String getName() {
    return null;
  }
}
