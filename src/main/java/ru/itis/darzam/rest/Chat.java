package ru.itis.darzam.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.darzam.model.Message;
import ru.itis.darzam.service.MessageService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class Chat {

    private final MessageService messageService;

    @GetMapping(path = "/api/chat")
    public String hello(Authentication authentication, @RequestBody Message message) {
        return authentication.getName() + " send new text " + message.getText();
    }


}
