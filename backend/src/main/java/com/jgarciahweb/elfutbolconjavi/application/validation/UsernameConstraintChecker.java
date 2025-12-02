package com.jgarciahweb.elfutbolconjavi.application.validation;

import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import com.jgarciahweb.elfutbolconjavi.domain.exceptions.UserAlreadyExistsException;
import com.jgarciahweb.elfutbolconjavi.domain.repositories.UserRepository;
import com.jgarciahweb.elfutbolconjavi.domain.validation.ConstraintChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UsernameConstraintChecker implements ConstraintChecker<RegisterUserCommand> {

    private final UserRepository userRepository;

    @Override
    public Mono<RegisterUserCommand> check(RegisterUserCommand command) {
        return userRepository.findByUsername(command.getUsername())
                .flatMap(user -> Mono.error(new UserAlreadyExistsException("El nombre de usuario ya existe")))
                .thenReturn(command);
    }
}
