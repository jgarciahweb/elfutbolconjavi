package com.jgarciahweb.elfutbolconjavi.apis.controller;

import com.jgarciahweb.elfutbolconjavi.apis.dto.RegisterUserRequestDTO;
import com.jgarciahweb.elfutbolconjavi.apis.dto.RegisterUserResponseDTO;
import com.jgarciahweb.elfutbolconjavi.apis.mapper.UserApiMapper;
import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import com.jgarciahweb.elfutbolconjavi.application.services.RegisterUserCommandHandler;
import com.jgarciahweb.elfutbolconjavi.domain.model.User;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private RegisterUserCommandHandler registerUserCommandHandler;

    @Mock
    private UserApiMapper userApiMapper;

    private WebTestClient webTestClient;

    private RegisterUserRequestDTO requestDTO;
    private RegisterUserCommand command;
    private User user;
    private RegisterUserResponseDTO responseDTO;
    private RegisterUserResponseDTO errorResponseDTO;

    @BeforeEach
    void setUp() {
        UserController controller =
                new UserController(registerUserCommandHandler, userApiMapper);

        webTestClient = WebTestClient.bindToController(controller).build();

        requestDTO = Instancio.create(RegisterUserRequestDTO.class);
        command = Instancio.create(RegisterUserCommand.class);
        user = Instancio.create(User.class);
        responseDTO = Instancio.create(RegisterUserResponseDTO.class);
        errorResponseDTO = Instancio.create(RegisterUserResponseDTO.class);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        // given
        given(userApiMapper.toCommand(requestDTO))
                .willReturn(command);

        given(registerUserCommandHandler.execute(command))
                .willReturn(Mono.just(user));

        given(userApiMapper.toResponse(user))
                .willReturn(responseDTO);

        // when / then
        webTestClient.post()
                .uri("/api/register")
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegisterUserResponseDTO.class)
                .isEqualTo(responseDTO);

        verify(userApiMapper).toCommand(requestDTO);
        verify(registerUserCommandHandler).execute(command);
        verify(userApiMapper).toResponse(user);
    }

    @Test
    void shouldReturnErrorResponseWhenExceptionOccurs() {
        // given
        given(userApiMapper.toCommand(requestDTO))
                .willReturn(command);

        given(registerUserCommandHandler.execute(command))
                .willReturn(Mono.error(new RuntimeException("boom")));

        given(userApiMapper.toErrorResponse("boom"))
                .willReturn(errorResponseDTO);

        // when / then
        webTestClient.post()
                .uri("/api/register")
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegisterUserResponseDTO.class)
                .isEqualTo(errorResponseDTO);

        verify(userApiMapper).toCommand(requestDTO);
        verify(registerUserCommandHandler).execute(command);
        verify(userApiMapper).toErrorResponse("boom");
    }
}
