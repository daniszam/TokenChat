package ru.itis.darzam.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Message {

    private String text;
    private UUID conversationId;
}
