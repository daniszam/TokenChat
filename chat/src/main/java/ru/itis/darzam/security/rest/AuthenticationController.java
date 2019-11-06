package ru.itis.darzam.security.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.darzam.enitity.User;
import ru.itis.darzam.security.model.JwtToken;
import ru.itis.darzam.dto.UserForm;
import ru.itis.darzam.service.JwtService;
import ru.itis.darzam.service.UserService;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

  private final JwtService jwtService;
  private final UserService userService;

  @PostMapping(path = "/auth")
  public JwtToken auth(@RequestBody UserForm userForm, HttpServletResponse response) throws AuthException {
    response.addHeader("Access-Control-Allow-Headers", "*");
    return jwtService.createToken(userForm);
  }

  @PostMapping(path = "/signUp")
  public void signUp(@RequestBody UserForm userForm, HttpServletResponse response) {
    User user = userService.createUserByForm(userForm);
    response.setStatus(user == null ? HttpStatus.BAD_REQUEST.value() : HttpStatus.OK.value());
  }
}

