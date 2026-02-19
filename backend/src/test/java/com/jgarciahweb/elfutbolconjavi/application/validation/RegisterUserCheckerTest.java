package com.jgarciahweb.elfutbolconjavi.application.validation;

import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import com.jgarciahweb.elfutbolconjavi.domain.exceptions.UserAlreadyExistsException;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.*;

class RegisterUserCheckerTest {

    private UsernameConstraintChecker usernameChecker;
    private EmailConstraintChecker emailChecker;
    private RegisterUserChecker registerUserChecker;
    private PasswordConstraintChecker passwordChecker;

    @BeforeEach
    void setUp() {
        // Arrange
        usernameChecker = mock(UsernameConstraintChecker.class);
        emailChecker = mock(EmailConstraintChecker.class);
        passwordChecker = mock(PasswordConstraintChecker.class);
        registerUserChecker = new RegisterUserChecker(usernameChecker, emailChecker, passwordChecker);
    }

    @Test
    void testCheck_AllGood_ShouldReturnCommand() {
        // Arrange
        RegisterUserCommand command = Instancio.create(RegisterUserCommand.class);

        given(usernameChecker.check(command)).willReturn(Mono.just(command));
        given(emailChecker.check(command)).willReturn(Mono.just(command));
        given(passwordChecker.check(command)).willReturn(Mono.just(command));

        // Act
        Mono<RegisterUserCommand> result = registerUserChecker.check(command);

        // Assert
        StepVerifier.create(result)
                .expectNext(command)
                .verifyComplete();

        then(usernameChecker).should().check(command);
        then(emailChecker).should().check(command);
        then(passwordChecker).should().check(command);
    }

    @Test
    void testCheck_EmailFails_ShouldThrowException() {
        // Arrange
        RegisterUserCommand command = Instancio.create(RegisterUserCommand.class);

        given(usernameChecker.check(command)).willReturn(Mono.just(command));
        given(emailChecker.check(command))
                .willReturn(Mono.error(new UserAlreadyExistsException("Email ya existe")));

        // Act
        Mono<RegisterUserCommand> result = registerUserChecker.check(command);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(ex -> ex instanceof UserAlreadyExistsException &&
                        ex.getMessage().equals("Email ya existe"))
                .verify();

        then(usernameChecker).should().check(command);
        then(emailChecker).should().check(command);
    }
}
