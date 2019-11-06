package ru.itis.darzam.service;

import ru.itis.darzam.dto.MessageDTO;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    void addMessage(MessageDTO messageDTO);
    List<String> getMessagesByConversationId(UUID uuid);
}
