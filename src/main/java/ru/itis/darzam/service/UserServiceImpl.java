package ru.itis.darzam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.darzam.enitity.User;
import ru.itis.darzam.exception.UserNotFoundException;
import ru.itis.darzam.repository.UserRepository;
import ru.itis.darzam.security.model.UserDetailsImpl;
import ru.itis.darzam.security.model.UserForm;

import javax.security.auth.message.AuthException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserByForm(UserForm userForm) throws AuthException {
        Optional<User> userOptional = userRepository.findUserByUsername(userForm.getUsername());

        userOptional.orElseThrow(() -> new UserNotFoundException("User not found"));

        User user = userOptional.get();
        if (!passwordEncoder.matches(userForm.getPassword(), user.getPassword())) {
            throw new AuthException("Incorrect username or password");
        }

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        return new UserDetailsImpl(user.orElse(new User()));
    }
}
