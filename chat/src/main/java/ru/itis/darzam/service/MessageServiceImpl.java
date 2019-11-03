package ru.itis.darzam.service;

import org.springframework.stereotype.Component;
import ru.itis.darzam.model.Message;

import java.util.*;

@Component
public class MessageServiceImpl implements MessageService {

    private Map<UUID, List<String>> messages = new HashMap<>();

    @Override
    public void addMessage(Message message) {
        List<String> messagesText =messages.getOrDefault(message.getConversationId(), new ArrayList<>());
        messagesText.add(message.getText());
        messages.put(message.getConversationId(), messagesText);
    }

    @Override
    public List<String> getMessagesByConversationId(UUID uuid) {
        return messages.getOrDefault(uuid, new ArrayList<>());
    }
}
