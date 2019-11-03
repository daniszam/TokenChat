package ru.itis.darzam.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itis.darzam.exception.UserNotFoundException;

import javax.security.auth.message.AuthException;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(value = AuthException.class)
    public ResponseEntity<String> notAuth(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<String> notFound(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    }
}
