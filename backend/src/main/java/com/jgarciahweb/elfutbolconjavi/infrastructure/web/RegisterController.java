package com.jgarciahweb.elfutbolconjavi.infrastructure.web;

import com.jgarciahweb.elfutbolconjavi.application.port.in.RegisterUserUseCase;
import com.jgarciahweb.elfutbolconjavi.infrastructure.web.dto.RegisterRequestDTO;
import com.jgarciahweb.elfutbolconjavi.infrastructure.web.dto.RegisterResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
public class RegisterController {
    private final RegisterUserUseCase registerUserUseCase;

    public RegisterController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Registra un usuario con nombre, email y contraseña.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario registrado con éxito",
                            content = @Content(schema = @Schema(implementation = RegisterResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error en el registro",
                            content = @Content(schema = @Schema(implementation = RegisterResponseDTO.class))
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        try {
            registerUserUseCase.registerUser(request);
            return ResponseEntity.ok(new RegisterResponseDTO(true, "Usuario registrado con éxito"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new RegisterResponseDTO(false, e.getMessage()));
        }
    }
}
