package ru.itis.darzam.security.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.darzam.security.model.JwtToken;
import ru.itis.darzam.security.model.UserForm;
import ru.itis.darzam.service.JwtService;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

  private final JwtService jwtService;

  @PostMapping(path = "/auth")
  public JwtToken auth(@RequestBody UserForm userForm, HttpServletResponse response) throws AuthException {
    response.addHeader("Access-Control-Allow-Headers", "*");
    return jwtService.createToken(userForm);
  }
}

