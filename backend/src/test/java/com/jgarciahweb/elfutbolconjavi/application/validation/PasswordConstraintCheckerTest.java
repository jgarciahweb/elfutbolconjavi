package com.jgarciahweb.elfutbolconjavi.application.validation;

import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

class PasswordConstraintCheckerTest {

    private PasswordConstraintChecker checker;

    @BeforeEach
    void setUp() {
        checker = new PasswordConstraintChecker();
    }

    private RegisterUserCommand commandWithPassword(String password) {
        return Instancio.of(RegisterUserCommand.class)
                .set(Select.field(RegisterUserCommand::getPassword), password)
                .create();
    }

    @Test
    void shouldReturnCommandWhenPasswordIsValid() {
        RegisterUserCommand command = commandWithPassword("Password1!");

        StepVerifier.create(checker.check(command))
                .expectNext(command)
                .verifyComplete();
    }

    @ParameterizedTest
    @MethodSource("invalidPasswords")
    void shouldReturnErrorWhenPasswordIsInvalid(String invalidPassword) {

        RegisterUserCommand command = commandWithPassword(invalidPassword);

        StepVerifier.create(checker.check(command))
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    private static Stream<Arguments> invalidPasswords() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, "password1!"),
                Arguments.of(null, "PASSWORD1!"),
                Arguments.of(null, "Password!"),
                Arguments.of(null, "Password1"),
                Arguments.of(null, "Pass word1!"),
                Arguments.of(null, "P1!")
        );
    }
}
