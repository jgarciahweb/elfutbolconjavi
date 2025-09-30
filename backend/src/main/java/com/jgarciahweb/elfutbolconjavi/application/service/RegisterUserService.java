package com.jgarciahweb.elfutbolconjavi.application.service;

import com.jgarciahweb.elfutbolconjavi.application.port.in.RegisterUserUseCase;
import com.jgarciahweb.elfutbolconjavi.application.port.out.SaveUserPort;
import com.jgarciahweb.elfutbolconjavi.domain.RoleEnum;
import com.jgarciahweb.elfutbolconjavi.domain.User;
import com.jgarciahweb.elfutbolconjavi.infrastructure.web.dto.RegisterRequestDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final SaveUserPort saveUserPort;

    public RegisterUserService(SaveUserPort saveUserPort) {
        this.saveUserPort = saveUserPort;
    }

    @Override
    public User registerUser(RegisterRequestDTO request) {
        return saveUserPort.save(
                User.builder()
                        .id(UUID.randomUUID().toString())
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .birthday(request.getBirthday())
                        .role(RoleEnum.NORMAL)
                        .isAcceptedCookies(false)
                        .isVerified(false)
                        .build()
        );
    }
}
