package com.jgarciahweb.elfutbolconjavi.application.port.out;

import com.jgarciahweb.elfutbolconjavi.domain.User;

import java.util.Optional;

public interface UserPort {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
