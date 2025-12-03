package com.jgarciahweb.elfutbolconjavi.apis.controller;

import com.jgarciahweb.elfutbolconjavi.apis.mapper.UserApiMapper;
import com.jgarciahweb.elfutbolconjavi.application.services.RegisterUserCommandHandler;
import com.jgarciahweb.elfutbolconjavi.apis.dto.RegisterUserRequestDTO;
import com.jgarciahweb.elfutbolconjavi.apis.dto.RegisterUserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
public class UserController {

    private final RegisterUserCommandHandler registerUserCommandHandler;
    private final UserApiMapper userApiMapper;

    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Registra un usuario con nombre, email y contraseña.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario registrado con éxito",
                            content = @Content(schema = @Schema(implementation = RegisterUserResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error en el registro",
                            content = @Content(schema = @Schema(implementation = RegisterUserResponseDTO.class))
                    )
            }
    )
    @PostMapping("/register")
    public Mono<RegisterUserResponseDTO> register(@RequestBody RegisterUserRequestDTO request) {
        return Mono.just(request)
                .map(userApiMapper::toCommand)
                .flatMap(registerUserCommandHandler::execute)
                .map(userApiMapper::toResponse)
                .onErrorResume(ex -> Mono.just(userApiMapper.toErrorResponse(ex.getMessage())));
    }
}
