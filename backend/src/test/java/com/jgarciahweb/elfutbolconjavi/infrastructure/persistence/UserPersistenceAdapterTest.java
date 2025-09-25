package com.jgarciahweb.elfutbolconjavi.infrastructure.persistence;

import com.jgarciahweb.elfutbolconjavi.domain.User;
import com.jgarciahweb.elfutbolconjavi.infrastructure.persistance.UserEntity;
import com.jgarciahweb.elfutbolconjavi.infrastructure.persistance.UserPersistenceAdapter;
import com.jgarciahweb.elfutbolconjavi.infrastructure.persistance.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserPersistenceAdapterTest {

    private UserRepository userRepository;
    private UserPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        adapter = new UserPersistenceAdapter(userRepository);
    }

    @Test
    void shouldSaveUserIfEmailNotExists() {
        User user = new User("1", "javi", "test@test.com", "123", LocalDate.now(), true, false);

        when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());

        User result = adapter.save(user);

        assertEquals("test@test.com", result.getEmail());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldThrowExceptionIfEmailExists() {
        User user = new User("1", "javi", "test@test.com", "123", LocalDate.now(), true, false);

        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> adapter.save(user));
        assertEquals("El email ya está registrado", ex.getMessage());
    }
}
