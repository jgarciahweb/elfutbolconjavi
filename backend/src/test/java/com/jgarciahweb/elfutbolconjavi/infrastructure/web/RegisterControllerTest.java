package com.jgarciahweb.elfutbolconjavi.infrastructure.web;

import com.jgarciahweb.elfutbolconjavi.application.port.in.RegisterUserUseCase;
import com.jgarciahweb.elfutbolconjavi.domain.User;
import com.jgarciahweb.elfutbolconjavi.infrastructure.web.dto.RegisterRequestDTO;
import com.jgarciahweb.elfutbolconjavi.infrastructure.web.dto.RegisterResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterControllerTest {

    private RegisterUserUseCase useCase;
    private RegisterController controller;

    @BeforeEach
    void setUp() {
        useCase = mock(RegisterUserUseCase.class);
        controller = new RegisterController(useCase);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("javi");
        request.setEmail("test@test.com");
        request.setPassword("123");

        when(useCase.registerUser("javi", "test@test.com", "123"))
                .thenReturn(new User("1", "javi", "test@test.com", "123"));

        ResponseEntity<RegisterResponseDTO> response = controller.register(request);

        assertTrue(response.getBody().isSuccess());
        assertEquals("Usuario registrado con éxito", response.getBody().getMessage());
    }

    @Test
    void shouldReturnBadRequestIfError() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("javi");
        request.setEmail("test@test.com");
        request.setPassword("123");

        when(useCase.registerUser(any(), any(), any())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<RegisterResponseDTO> response = controller.register(request);

        assertFalse(response.getBody().isSuccess());
        assertEquals("Error", response.getBody().getMessage());
    }
}
