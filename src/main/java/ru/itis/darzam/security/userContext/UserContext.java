package ru.itis.darzam.security.userContext;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserContext {

  private String username;

  public static UserContext create(String username) {
    return new UserContext(username);
  }
}
