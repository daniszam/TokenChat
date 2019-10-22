package ru.itis.darzam.security.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import ru.itis.darzam.security.authentication.JWTAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends AbstractAuthenticationProcessingFilter {

  private static final String AUTHENTICATION_PATH = "/chat";
  private static final String AUTHENTICATION_HEADER = "Authorize";

  public JWTFilter() {
    super(AUTHENTICATION_PATH);
  }

  public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
    String token = httpServletRequest.getHeader(AUTHENTICATION_HEADER);
    JWTAuthentication jwtAuthentication = new JWTAuthentication(token);
    if (token == null) {
      jwtAuthentication.setAuthenticated(true);
      return jwtAuthentication;
    }

    return getAuthenticationManager()
            .authenticate(jwtAuthentication);
  }
}
