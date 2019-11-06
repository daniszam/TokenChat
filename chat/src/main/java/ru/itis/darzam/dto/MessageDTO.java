package ru.itis.darzam.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MessageDTO {

  private String text;
  private UUID conversationId;
}
