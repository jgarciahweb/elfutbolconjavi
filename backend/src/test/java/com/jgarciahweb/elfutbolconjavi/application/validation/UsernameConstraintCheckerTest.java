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

class UsernameConstraintCheckerTest {

    private UserRepository userRepository;
    private UsernameConstraintChecker checker;

    @BeforeEach
    void setUp() {
        // Arrange
        userRepository = Mockito.mock(UserRepository.class);
        checker = new UsernameConstraintChecker(userRepository);
    }

    @Test
    void shouldReturnCommand() {
        // Arrange
        RegisterUserCommand command = Instancio.create(RegisterUserCommand.class);
        given(userRepository.findByUsername(command.getUsername())).willReturn(Mono.empty());

        // Act
        Mono<RegisterUserCommand> result = checker.check(command);

        // Assert
        StepVerifier.create(result)
                .expectNext(command)
                .verifyComplete();

        verify(userRepository).findByUsername(command.getUsername());
    }

    @Test
    void shouldThrowUserAlreadyExistsException() {
        // Arrange
        RegisterUserCommand command = Instancio.create(RegisterUserCommand.class);
        User existingUser = Instancio.create(User.class);

        given(userRepository.findByUsername(command.getUsername())).willReturn(Mono.just(existingUser));

        // Act
        Mono<RegisterUserCommand> result = checker.check(command);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(ex -> ex instanceof UserAlreadyExistsException &&
                        ex.getMessage().equals("El nombre de usuario ya existe"))
                .verify();

        verify(userRepository).findByUsername(command.getUsername());
    }
}
