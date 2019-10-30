package ru.itis.darzam.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.itis.darzam.enitity.User;
import ru.itis.darzam.security.model.UserForm;

import javax.security.auth.message.AuthException;


public interface UserService {

    User getUserByForm(UserForm userForm) throws AuthException;
}
