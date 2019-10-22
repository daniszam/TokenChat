package ru.itis.darzam.security.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.darzam.security.jwtTokenFactory.JWTFactory;
import ru.itis.darzam.security.model.JwtToken;
import ru.itis.darzam.security.userContext.UserContext;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

  private final JWTFactory jwtFactory;

  @GetMapping(path = "/auth")
  public JwtToken auth(UserContext userContext) {
    return jwtFactory.createToken(userContext);
  }
}

