package com.jgarciahweb.elfutbolconjavi.application.port.in;

import com.jgarciahweb.elfutbolconjavi.domain.User;
import com.jgarciahweb.elfutbolconjavi.infrastructure.web.dto.RegisterRequestDTO;

public interface UserUseCase {
    User registerUser(RegisterRequestDTO request);
}
