package ru.itis.darzam.security.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.itis.darzam.security.authentication.JWTAuthentication;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JWTTokenProvider implements AuthenticationProvider {

    @Value("${secret: secret}")
    private String secret;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JWTAuthentication jwtAuthentication = (JWTAuthentication) authentication;
        String token = jwtAuthentication.getToken();

        try {

            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            List<String> scopes = body.get("scopes", List.class);
            List<GrantedAuthority> authorities = scopes.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            jwtAuthentication.setGrantedAuthorities(authorities);
            jwtAuthentication.setPrincipal(body.getSubject());
            jwtAuthentication.setAuthenticated(true);

        } catch (ExpiredJwtException e){
            throw new BadCredentialsException("Token expired");
        } catch (Exception e) {
           throw new BadCredentialsException("Bad jwt token");
        }

        return jwtAuthentication;
    }

    public boolean supports(Class<?> aClass) {
        return JWTAuthentication.class.isAssignableFrom(aClass);
    }
}
