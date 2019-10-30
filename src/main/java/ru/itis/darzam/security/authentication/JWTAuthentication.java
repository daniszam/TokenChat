package ru.itis.darzam.security.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JWTAuthentication implements Authentication {

  private String token;
  private boolean authenticated;
  private Collection<? extends GrantedAuthority> grantedAuthorities;
  private String principal;

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return grantedAuthorities;
  }

  public Object getCredentials() {
    return null;
  }

  public Object getDetails() {
    return null;
  }

  public Object getPrincipal() {
    return principal;
  }

  public boolean isAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(boolean b) throws IllegalArgumentException {
    authenticated = b;
  }

  public String getName() {
    return principal;
  }
}
