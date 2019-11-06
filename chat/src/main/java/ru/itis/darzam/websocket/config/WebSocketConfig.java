package ru.itis.darzam.websocket.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeHandler;
import ru.itis.darzam.websocket.handler.AuthHandShakeHandler;

@EnableWebSocket
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

  @Value("${chat.api.websocket: /api/chat/websocket}")
  private String WEBSOCKET_URL;

  private final TextWebSocketHandler webSocketHandler;
  private final AuthHandShakeHandler handshakeHandler;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
    webSocketHandlerRegistry.addHandler(webSocketHandler, WEBSOCKET_URL).setAllowedOrigins("*")
            .setHandshakeHandler(handshakeHandler);
  }
}
