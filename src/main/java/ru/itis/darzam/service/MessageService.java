package ru.itis.darzam.service;

import ru.itis.darzam.model.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    void addMessage(Message message);
    List<String> getMessagesByConversationId(UUID uuid);
}
