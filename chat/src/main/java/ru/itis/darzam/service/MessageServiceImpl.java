package ru.itis.darzam.service;

import org.springframework.stereotype.Component;
import ru.itis.darzam.dto.MessageDTO;

import java.util.*;

@Component
public class MessageServiceImpl implements MessageService {

    private Map<String, List<String>> messages = new HashMap<>();

    @Override
    public void addMessage(MessageDTO messageDTO) {
        List<String> messagesText =messages.getOrDefault(messageDTO.getPageId(), new ArrayList<>());
        messagesText.add(messageDTO.getText());
        messages.put(messageDTO.getPageId(), messagesText);
    }

    @Override
    public List<String> getMessagesByConversationId(UUID uuid) {
        return messages.getOrDefault(uuid, new ArrayList<>());
    }
}
