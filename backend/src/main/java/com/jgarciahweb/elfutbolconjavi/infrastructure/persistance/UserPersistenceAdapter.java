package com.jgarciahweb.elfutbolconjavi.infrastructure.persistance;

import com.jgarciahweb.elfutbolconjavi.application.port.out.SaveUserPort;
import com.jgarciahweb.elfutbolconjavi.domain.User;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class UserPersistenceAdapter implements SaveUserPort {

    private final UserRepository userRepository;

    public UserPersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

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
}
