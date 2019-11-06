package ru.itis.darzam.websocket.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.WebUtils;
import ru.itis.darzam.security.authentication.JWTAuthentication;

import java.util.Map;

import static ru.itis.darzam.security.config.SecurityConfig.JWT_TOKEN_HEADER_PARAM;

@Component
@RequiredArgsConstructor
public class AuthHandShakeHandler implements HandshakeHandler {

  private final DefaultHandshakeHandler handshakeHandler = new DefaultHandshakeHandler();
  private final AuthenticationManager authenticationManager;

  @Override
  public boolean doHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws HandshakeFailureException {
    ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
    String accessToken = WebUtils.getCookie(request.getServletRequest(), JWT_TOKEN_HEADER_PARAM).getValue();
    Authentication authentication = new JWTAuthentication(accessToken);
    authenticationManager.authenticate(authentication);
    if (authentication.isAuthenticated()){
      return handshakeHandler.doHandshake(serverHttpRequest, serverHttpResponse, webSocketHandler, map);
    } else {
      serverHttpResponse.setStatusCode(HttpStatus.FORBIDDEN);
      return false;
    }
  }
}
