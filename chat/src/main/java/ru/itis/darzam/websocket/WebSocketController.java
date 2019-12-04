package ru.itis.darzam.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

@Controller
public class WebSocketController {

  @MessageMapping("/api/chat/stomp/{chatID}")
  @SendTo("/topic/messages/{chatId}")
  public TextMessage send(WebSocketMessage message) {
    return (TextMessage) message;
  }

}
