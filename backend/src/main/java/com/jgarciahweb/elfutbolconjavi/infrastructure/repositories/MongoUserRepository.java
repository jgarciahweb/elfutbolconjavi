package com.jgarciahweb.elfutbolconjavi.infrastructure.repositories;

import com.jgarciahweb.elfutbolconjavi.domain.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface MongoUserRepository extends ReactiveMongoRepository<User, String> {

    Mono<Boolean> existsByEmail(String email);
    Mono<User> findByEmail(String email);
    Mono<User> findByUsername(String username);
}
