package com.jgarciahweb.elfutbolconjavi.domain.repositories;

import com.jgarciahweb.elfutbolconjavi.domain.model.User;
import reactor.core.publisher.Mono;


public interface UserRepository {
    Mono<Boolean> existsByEmail(String email);
    Mono<User> findByEmail(String email);
    Mono<User> findByUsername(String username);
    Mono<User> save(User user);
}
