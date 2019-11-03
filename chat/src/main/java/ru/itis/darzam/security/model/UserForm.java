package ru.itis.darzam.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserForm {

  private String username;
  private String password;

  public static UserForm create(String username, String password) {
    return new UserForm(username, password);
  }
}
