package com.jgarciahweb.elfutbolconjavi.application.validation;

import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import com.jgarciahweb.elfutbolconjavi.domain.exceptions.UserAlreadyExistsException;
import com.jgarciahweb.elfutbolconjavi.domain.model.User;
import com.jgarciahweb.elfutbolconjavi.domain.repositories.UserRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class EmailConstraintCheckerTest {

    private UserRepository userRepository;
    private EmailConstraintChecker checker;

    @BeforeEach
    void setUp() {
        // Arrange
        userRepository = Mockito.mock(UserRepository.class);
        checker = new EmailConstraintChecker(userRepository);
    }

    @Test
    void shouldReturnCommand() {
        // Arrange
        RegisterUserCommand command = Instancio.create(RegisterUserCommand.class);
        given(userRepository.findByEmail(command.getEmail())).willReturn(Mono.empty());

        // Act
        Mono<RegisterUserCommand> result = checker.check(command);

        // Assert
        StepVerifier.create(result)
                .expectNext(command)
                .verifyComplete();

        verify(userRepository).findByEmail(command.getEmail());
    }

    @Test
    void shouldThrowUserAlreadyExistsException() {
        // Arrange
        RegisterUserCommand command = Instancio.create(RegisterUserCommand.class);
        User existingUser = Instancio.create(User.class);

        given(userRepository.findByEmail(command.getEmail())).willReturn(Mono.just(existingUser));

        // Act
        Mono<RegisterUserCommand> result = checker.check(command);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(ex -> ex instanceof UserAlreadyExistsException &&
                        ex.getMessage().equals("Este email ya está registrado"))
                .verify();

        verify(userRepository).findByEmail(command.getEmail());
    }
}
