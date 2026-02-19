package com.jgarciahweb.elfutbolconjavi.application.validation;

import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import com.jgarciahweb.elfutbolconjavi.domain.validation.ConstraintChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PasswordConstraintChecker implements ConstraintChecker<RegisterUserCommand> {

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d])\\S{8,}$";

    @Override
    public Mono<RegisterUserCommand> check(RegisterUserCommand command) {
        if (command.getPassword() == null || !command.getPassword().matches(PASSWORD_REGEX)) {
            return Mono.error(new IllegalArgumentException(
                    "La contraseña no cumple todos los requisitos"
            ));
        }

        return Mono.just(command);
    }
}
