package ru.itis.darzam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zamaliev
 */
@RestController
public class HelloWorld {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
