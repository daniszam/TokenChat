package ru.itis.darzam.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtToken {

  private String accessToken;
  private String refreshToken;
}
