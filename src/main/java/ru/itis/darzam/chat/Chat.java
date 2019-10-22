package ru.itis.darzam.chat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Chat {

  @GetMapping(path = "/chat")
  public String hello(){
    return "Hello";
  }
}
