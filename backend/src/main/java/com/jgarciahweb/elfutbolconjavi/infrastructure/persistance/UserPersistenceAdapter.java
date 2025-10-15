package com.jgarciahweb.elfutbolconjavi.infrastructure.persistance;

import com.jgarciahweb.elfutbolconjavi.application.port.out.UserPort;
import com.jgarciahweb.elfutbolconjavi.domain.User;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.Optional;

@Component
public class UserPersistenceAdapter implements UserPort {

    private final UserRepository userRepository;

    public UserPersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setBirthdate(user.getBirthday().atStartOfDay(ZoneOffset.UTC).toInstant());
        entity.setAcceptedCookies(user.isAcceptedCookies());
        entity.setVerified(user.isVerified());

        userRepository.save(entity);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
