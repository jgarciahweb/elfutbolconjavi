package com.jgarciahweb.elfutbolconjavi.application.validation;

import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RegisterUserChecker {

    private final UsernameConstraintChecker usernameConstraintChecker;
    private final EmailConstraintChecker emailConstraintChecker;
    private final PasswordConstraintChecker passwordConstraintChecker;

    public Mono<RegisterUserCommand> check(RegisterUserCommand command) {
        return usernameConstraintChecker.check(command)
                .flatMap(emailConstraintChecker::check)
                .flatMap(passwordConstraintChecker::check);
    }

}