package ru.itis.darzam.service;


import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.darzam.security.model.JwtToken;
import ru.itis.darzam.security.model.UserForm;

import javax.security.auth.message.AuthException;

public interface JwtService {

    JwtToken createToken(UserForm userForm) throws AuthException;

    UserDetails getUserByToken(JwtToken jwtToken);
}
