package com.jgarciahweb.elfutbolconjavi.infrastructure.web;

import com.jgarciahweb.elfutbolconjavi.application.port.in.RegisterUserUseCase;
import com.jgarciahweb.elfutbolconjavi.domain.RoleEnum;
import com.jgarciahweb.elfutbolconjavi.domain.User;
import com.jgarciahweb.elfutbolconjavi.infrastructure.web.dto.RegisterRequestDTO;
import com.jgarciahweb.elfutbolconjavi.infrastructure.web.dto.RegisterResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

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
        request.setBirthday(LocalDate.now());
        request.setAcceptedCookies(true);

        when(useCase.registerUser(request))
                .thenReturn(new User("1", "javi", "test@test.com", "123", RoleEnum.NORMAL, LocalDate.now(), true, false));

        ResponseEntity<RegisterResponseDTO> response = controller.register(request);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Usuario registrado con éxito", response.getBody().getMessage());
    }

    @Test
    void shouldReturnBadRequestIfError() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("javi");
        request.setEmail("test@test.com");
        request.setPassword("123");
        request.setBirthday(LocalDate.now());
        request.setAcceptedCookies(true);

        when(useCase.registerUser(any())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<RegisterResponseDTO> response = controller.register(request);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Error", response.getBody().getMessage());
    }
}
