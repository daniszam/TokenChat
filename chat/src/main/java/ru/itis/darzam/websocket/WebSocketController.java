package ru.itis.darzam.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.itis.darzam.dto.StomMessageDTO;

@Controller
public class WebSocketController {

  @MessageMapping("/api/dialog/{chatID}")
  @SendTo("/topic/messages/{chatID}")
  public StomMessageDTO send(@DestinationVariable String chatID, StomMessageDTO message) {
    return message;
  }

}
