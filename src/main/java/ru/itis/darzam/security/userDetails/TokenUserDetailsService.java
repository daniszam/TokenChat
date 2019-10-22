package ru.itis.darzam.security.userDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class TokenUserDetailsService implements UserDetailsService {

//  private

  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    return null;
  }
}
