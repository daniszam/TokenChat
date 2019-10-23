package ru.itis.darzam.enitity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "chat_user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
}
