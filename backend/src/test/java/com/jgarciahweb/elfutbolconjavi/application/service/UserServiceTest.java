package com.jgarciahweb.elfutbolconjavi.application.service;

import com.jgarciahweb.elfutbolconjavi.application.port.out.UserPort;
import com.jgarciahweb.elfutbolconjavi.domain.RoleEnum;
import com.jgarciahweb.elfutbolconjavi.domain.User;
import com.jgarciahweb.elfutbolconjavi.domain.exception.UserAlreadyExistsException;
import com.jgarciahweb.elfutbolconjavi.infrastructure.web.dto.RegisterRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserPort userPort;
    private PasswordEncoder passwordEncoder;
    private UserService service;

    @BeforeEach
    void setUp() {
        userPort = Mockito.mock(UserPort.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        service = new UserService(userPort, passwordEncoder);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        var localDate = LocalDate.now();

        User expected = new User("123", "javi", "javi@test.com", "pass", RoleEnum.NORMAL, localDate, true, false );

        when(userPort.save(any(User.class))).thenReturn(expected);

        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("javi");
        request.setEmail("javi@test.com");
        request.setPassword("pass");
        request.setBirthday(localDate);

        User result = service.registerUser(request);

        assertEquals("javi@test.com", result.getEmail());
        verify(userPort, times(1)).save(any(User.class));
    }
    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("javi");
        request.setEmail("javi@test.com");
        request.setPassword("pass");
        request.setBirthday(LocalDate.now());

        when(userPort.findByEmail("javi@test.com")).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> service.registerUser(request));

        verify(userPort, never()).save(any());
        verify(userPort, never()).findByUsername(any());
    }

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("javi");
        request.setEmail("javi@test.com");
        request.setPassword("pass");
        request.setBirthday(LocalDate.now());

        when(userPort.findByEmail("javi@test.com")).thenReturn(Optional.empty());
        when(userPort.findByUsername("javi")).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> service.registerUser(request));

        verify(userPort, never()).save(any());
    }

}
