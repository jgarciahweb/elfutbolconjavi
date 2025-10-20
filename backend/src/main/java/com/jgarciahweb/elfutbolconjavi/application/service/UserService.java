package com.jgarciahweb.elfutbolconjavi.application.service;

import com.jgarciahweb.elfutbolconjavi.application.port.in.UserUseCase;
import com.jgarciahweb.elfutbolconjavi.application.port.out.UserPort;
import com.jgarciahweb.elfutbolconjavi.domain.RoleEnum;
import com.jgarciahweb.elfutbolconjavi.domain.User;
import com.jgarciahweb.elfutbolconjavi.domain.exception.UserAlreadyExistsException;
import com.jgarciahweb.elfutbolconjavi.infrastructure.web.dto.RegisterRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserUseCase {

    private final UserPort userPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterRequestDTO request) {

        userPort.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Este email ya está registrado");
                });

        userPort.findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("El nombre de usuario ya existe");
                });

        return userPort.save(
                User.builder()
                        .id(generateShortId())
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .birthday(request.getBirthday())
                        .role(RoleEnum.NORMAL)
                        .isAcceptedCookies(false)
                        .isVerified(false)
                        .build()
        );
    }

    private String generateShortId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
