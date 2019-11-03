package ru.itis.darzam.enitity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "chat_user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @ManyToMany(mappedBy = "users")
    private Set<Conversation> conversations;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Message> messages;
}
