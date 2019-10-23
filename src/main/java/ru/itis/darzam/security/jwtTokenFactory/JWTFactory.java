package ru.itis.darzam.security.jwtTokenFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.itis.darzam.security.model.JwtToken;

import javax.security.auth.message.AuthException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JWTFactory {

  @Value("${secret: secret}")
  private String secret;
  @Value("${access.lifeTime: 10000}")
  private Integer lifeTimeInMs;
  @Value("${refresh.lifeTime: 10000}")
  private Integer refreshLifeTimeInMs;

  public JwtToken createToken(UserDetails userDetails) throws AuthException {

    Claims claims = Jwts.claims()
            .setSubject(userDetails.getUsername());

    claims.put("role", userDetails.getAuthorities());

    Calendar calendar = Calendar.getInstance();
    Date now = new Date();
    calendar.setTime(now);
    calendar.add(Calendar.MILLISECOND, lifeTimeInMs);

    String accessToken = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(calendar.getTime())
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();

    String refreshToken = Jwts.builder()
            .setClaims(claims)
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(now)
            .setExpiration(calendar.getTime())
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();

    return new JwtToken(accessToken, refreshToken);
  }
}
