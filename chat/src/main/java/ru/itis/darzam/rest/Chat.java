package ru.itis.darzam.rest;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.darzam.dto.MessageDTO;
import ru.itis.darzam.enitity.Message;
import ru.itis.darzam.service.MessageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class Chat {

    private final Map<String, List<MessageDTO>> messages = new HashMap<>();

    @PostMapping("/api/chat/long-pooling")
    public ResponseEntity<Object> receiveMessage(@RequestBody MessageDTO message) {
        for (List<MessageDTO> pageMessages : messages.values()) {
            synchronized (pageMessages) {
                pageMessages.add(message);
                pageMessages.notifyAll();
            }
        }
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @GetMapping("/api/chat/long-pooling")
    public ResponseEntity<List<MessageDTO>> getMessagesForPage(@RequestParam("pageId") String pageId) {
        if (!messages.containsKey(pageId)) {
            messages.put(pageId, new ArrayList<>());
        }
        synchronized (messages.get(pageId)) {
            if (messages.get(pageId).isEmpty()) {
                messages.get(pageId).wait();
            }
            List<MessageDTO> response = new ArrayList<>(messages.get(pageId));
            messages.get(pageId).clear();
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/api/chat/long-pooling/connect")
    public String getChatPage() {
        return UUID.randomUUID().toString();
    }
}
