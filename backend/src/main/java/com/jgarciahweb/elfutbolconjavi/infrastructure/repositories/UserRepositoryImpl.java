package com.jgarciahweb.elfutbolconjavi.infrastructure.repositories;

import com.jgarciahweb.elfutbolconjavi.domain.model.User;
import com.jgarciahweb.elfutbolconjavi.domain.repositories.UserRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final MongoUserRepository mongoUserRepository;

    public UserRepositoryImpl(MongoUserRepository mongoUserRepository) {
        this.mongoUserRepository = mongoUserRepository;
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return mongoUserRepository.existsByEmail(email);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return mongoUserRepository.findByEmail(email);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return mongoUserRepository.findByUsername(username);
    }

    @Override
    public Mono<User> save(User user) {
        return mongoUserRepository.save(user);
    }
}
