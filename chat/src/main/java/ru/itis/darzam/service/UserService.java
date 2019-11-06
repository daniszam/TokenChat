package ru.itis.darzam.service;

import ru.itis.darzam.enitity.User;
import ru.itis.darzam.dto.UserForm;

import javax.security.auth.message.AuthException;


public interface UserService {

    User getUserByForm(UserForm userForm) throws AuthException;
    User createUserByForm(UserForm userForm);
}
