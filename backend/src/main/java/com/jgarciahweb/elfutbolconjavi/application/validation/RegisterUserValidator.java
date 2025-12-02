package com.jgarciahweb.elfutbolconjavi.application.validation;

import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import com.jgarciahweb.elfutbolconjavi.domain.exceptions.UserAlreadyExistsException;
import com.jgarciahweb.elfutbolconjavi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RegisterUserValidator {

    private final UserRepository userRepository;

    public Mono<Void> validate(RegisterUserCommand command) {

        return userRepository.findByUsername(command.getUsername())
                .flatMap(user -> Mono.error(new UserAlreadyExistsException("El nombre de usuario ya existe")))
                .switchIfEmpty(
                        userRepository.findByEmail(command.getEmail())
                                .flatMap(user -> Mono.error(new UserAlreadyExistsException("Este email ya está registrado")))
                )
                .then();
    }
}
