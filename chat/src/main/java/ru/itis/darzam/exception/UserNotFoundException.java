package ru.itis.darzam.exception;

import javax.security.auth.message.AuthException;

public class UserNotFoundException extends AuthException {

    public UserNotFoundException (String message){
        super(message);
    }
}
