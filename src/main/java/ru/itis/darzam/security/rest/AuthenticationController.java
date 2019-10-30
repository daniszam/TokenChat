package ru.itis.darzam.security.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.darzam.security.model.JwtToken;
import ru.itis.darzam.security.model.UserForm;
import ru.itis.darzam.service.JwtService;

import javax.security.auth.message.AuthException;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

  private final JwtService jwtService;

  @PostMapping(path = "/auth")
  public JwtToken auth(@RequestBody UserForm userForm) throws AuthException {
    return jwtService.createToken(userForm);
  }
}

