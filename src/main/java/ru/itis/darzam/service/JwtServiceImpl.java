package ru.itis.darzam.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.itis.darzam.enitity.User;
import ru.itis.darzam.security.jwtTokenFactory.JWTFactory;
import ru.itis.darzam.security.model.JwtToken;
import ru.itis.darzam.security.model.UserDetailsImpl;
import ru.itis.darzam.security.model.UserForm;

import javax.security.auth.message.AuthException;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JWTFactory jwtFactory;
    private final UserDetailsService userService;

    @Value("${secret: secret}")
    private String secret;


    @Override
    public JwtToken createToken(UserForm userForm) throws AuthException {
        UserDetails userDetails = userService.loadUserByUsername(userForm.getUsername());
        return jwtFactory.createToken(userDetails);
    }

    @Override
    public UserDetails getUserByToken(JwtToken jwtToken) {
        Claims body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwtToken.getAccessToken())
                .getBody();
        return userService.loadUserByUsername(body.getSubject());

    }
}
