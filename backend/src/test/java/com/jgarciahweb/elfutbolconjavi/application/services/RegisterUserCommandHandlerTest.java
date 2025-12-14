package com.jgarciahweb.elfutbolconjavi.application.services;

import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import com.jgarciahweb.elfutbolconjavi.application.validation.RegisterUserChecker;
import com.jgarciahweb.elfutbolconjavi.domain.mappers.UserMapper;
import com.jgarciahweb.elfutbolconjavi.domain.model.User;
import com.jgarciahweb.elfutbolconjavi.domain.repositories.UserRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class RegisterUserCommandHandlerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RegisterUserChecker registerUserChecker;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterUserCommandHandler handler;

    private RegisterUserCommand command;
    private User user;

    @BeforeEach
    void setUp() {
        command = Instancio.create(RegisterUserCommand.class);
        user = Instancio.create(User.class);
        user.setPassword("plain-password");
    }

    @Test
    void shouldRegisterUserAndEncodePassword() {
        // given
        given(registerUserChecker.check(command))
                .willReturn(Mono.just(command));

        given(userMapper.toDomain(command))
                .willReturn(user);

        given(passwordEncoder.encode("plain-password"))
                .willReturn("encoded-password");

        given(userRepository.save(any(User.class)))
                .willAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // when
        Mono<User> result = handler.execute(command);

        // then
        StepVerifier.create(result)
                .assertNext(savedUser ->
                        assertThat(savedUser.getPassword())
                                .isEqualTo("encoded-password")
                )
                .verifyComplete();

        verify(registerUserChecker).check(command);
        verify(userMapper).toDomain(command);
        verify(passwordEncoder).encode("plain-password");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldNotHaveUserWhenValidationFails() {
        // given
        given(registerUserChecker.check(command))
                .willReturn(Mono.error(new IllegalArgumentException("Invalid command")));

        // when
        Mono<User> result = handler.execute(command);

        // then
        StepVerifier.create(result)
                .expectError(IllegalArgumentException.class)
                .verify();

        verifyNoInteractions(userMapper, userRepository, passwordEncoder);
    }
}
