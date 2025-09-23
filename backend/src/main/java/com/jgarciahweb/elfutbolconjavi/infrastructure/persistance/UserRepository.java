package com.jgarciahweb.elfutbolconjavi.infrastructure.persistance;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    boolean exitsByEmail(String email);
}
