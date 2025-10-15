package com.jgarciahweb.elfutbolconjavi.infrastructure.persistance;

import com.jgarciahweb.elfutbolconjavi.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
