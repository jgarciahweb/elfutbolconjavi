package com.jgarciahweb.elfutbolconjavi.application.service;

import com.jgarciahweb.elfutbolconjavi.application.port.out.SaveUserPort;
import com.jgarciahweb.elfutbolconjavi.domain.RoleEnum;
import com.jgarciahweb.elfutbolconjavi.domain.User;
import com.jgarciahweb.elfutbolconjavi.infrastructure.web.dto.RegisterRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterUserServiceTest {

    private SaveUserPort saveUserPort;
    private RegisterUserService service;

    @BeforeEach
    void setUp() {
        saveUserPort = Mockito.mock(SaveUserPort.class);
        service = new RegisterUserService(saveUserPort);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        var localDate = LocalDate.now();

        User expected = new User("123", "javi", "javi@test.com", "pass", RoleEnum.NORMAL, localDate, true, false );

        when(saveUserPort.save(any(User.class))).thenReturn(expected);

        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("javi");
        request.setEmail("javi@test.com");
        request.setPassword("pass");
        request.setBirthday(localDate);

        // when
        User result = service.registerUser(request);

        assertEquals("javi@test.com", result.getEmail());
        verify(saveUserPort, times(1)).save(any(User.class));
    }
}
