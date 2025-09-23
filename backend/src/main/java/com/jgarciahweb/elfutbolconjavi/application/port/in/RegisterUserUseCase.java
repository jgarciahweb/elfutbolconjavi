package com.jgarciahweb.elfutbolconjavi.application.port.in;

import com.jgarciahweb.elfutbolconjavi.domain.User;

public interface RegisterUserUseCase {
    User registerUser(String username, String email, String password);
}
