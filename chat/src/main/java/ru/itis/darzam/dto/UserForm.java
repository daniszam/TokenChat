package ru.itis.darzam.dto;

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
  private String comparePassword;

  public static UserForm create(String username, String password, String comparePassword) {
    return new UserForm(username, password, comparePassword);
  }
}
