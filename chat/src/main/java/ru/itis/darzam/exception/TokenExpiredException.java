package ru.itis.darzam.exception;

import javax.security.auth.message.AuthException;

public class TokenExpiredException extends AuthException {

  public TokenExpiredException (){
    super("Token expired");
  }

}
