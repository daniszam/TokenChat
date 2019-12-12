package ru.itis.darZam.zuul;

import com.github.mthizo247.cloud.netflix.zuul.web.socket.EnableZuulWebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

/**
 * @author: zamaliev
 */
@EnableZuulProxy
@SpringBootApplication
@EnableEurekaClient
//@EnableZuulWebSocket
//@EnableWebSocketMessageBroker
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class);
    }
}
