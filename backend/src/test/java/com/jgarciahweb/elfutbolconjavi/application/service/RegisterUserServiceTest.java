package com.jgarciahweb.elfutbolconjavi.application.service;

import com.jgarciahweb.elfutbolconjavi.application.port.out.SaveUserPort;
import com.jgarciahweb.elfutbolconjavi.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        User expected = new User("123", "javi", "javi@test.com", "pass");

        when(saveUserPort.save(any(User.class))).thenReturn(expected);

        User result = service.registerUser("javi", "javi@test.com", "pass");

        assertEquals("javi@test.com", result.getEmail());
        verify(saveUserPort, times(1)).save(any(User.class));
    }
}
