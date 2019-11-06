package ru.itis.darzam.websocket.handler;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.darzam.enitity.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TextWebSocketHandlerImpl extends TextWebSocketHandler {

  private List<WebSocketSession> sessions = new ArrayList<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    sessions.add(session);
  }

  @Override
  protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage textMessage) throws Exception {
    Message message = new Gson().fromJson(textMessage.getPayload(), Message.class);

    sessions.removeIf(session -> {
      try {
        if (!session.isOpen()) {
          session.close();
          return true;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      return false;
    });

    sessions.forEach(session -> {
      try {
        session.sendMessage(textMessage);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }
}
