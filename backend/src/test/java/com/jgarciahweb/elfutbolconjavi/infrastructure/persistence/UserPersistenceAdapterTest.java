package com.jgarciahweb.elfutbolconjavi.infrastructure.persistence;

import com.jgarciahweb.elfutbolconjavi.domain.RoleEnum;
import com.jgarciahweb.elfutbolconjavi.domain.User;
import com.jgarciahweb.elfutbolconjavi.infrastructure.persistance.UserEntity;
import com.jgarciahweb.elfutbolconjavi.infrastructure.persistance.UserPersistenceAdapter;
import com.jgarciahweb.elfutbolconjavi.infrastructure.persistance.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

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
        User user = new User("1", "javi", "test@test.com", "123",
                RoleEnum.NORMAL, LocalDate.of(1990, 1, 1), true, false);

        when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());

        User result = adapter.save(user);

        assertEquals("test@test.com", result.getEmail());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void shouldMapUserFieldsToEntityCorrectly() {
        LocalDate birthday = LocalDate.of(2000, 5, 10);
        User user = new User("abc123", "ana", "ana@test.com", "pwd",
                RoleEnum.NORMAL, birthday, false, true);

        when(userRepository.existsByEmail("ana@test.com")).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());

        adapter.save(user);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        UserEntity entity = captor.getValue();

        assertEquals("abc123", entity.getId());
        assertEquals("ana", entity.getUsername());
        assertEquals("ana@test.com", entity.getEmail());
        assertEquals("pwd", entity.getPassword());
        assertEquals(birthday.atStartOfDay(ZoneOffset.UTC).toInstant(), entity.getBirthdate());
        assertFalse(entity.isAcceptedCookies());
        assertTrue(entity.isVerified());
    }

    @Test
    void shouldReturnUserWhenFoundByEmail() {
        User user = new User("1", "pepe", "pepe@test.com", "pwd",
                RoleEnum.NORMAL, LocalDate.now(), false, false);

        when(userRepository.findByEmail("pepe@test.com")).thenReturn(Optional.of(user));

        Optional<User> result = adapter.findByEmail("pepe@test.com");

        assertTrue(result.isPresent());
        assertEquals("pepe@test.com", result.get().getEmail());
        verify(userRepository).findByEmail("pepe@test.com");
    }

    @Test
    void shouldReturnUserWhenFoundByUsername() {
        User user = new User("1", "pepe", "pepe@test.com", "pwd",
                RoleEnum.NORMAL, LocalDate.now(), false, false);

        when(userRepository.findByUsername("pepe")).thenReturn(Optional.of(user));

        Optional<User> result = adapter.findByUsername("pepe");

        assertTrue(result.isPresent());
        assertEquals("pepe", result.get().getUsername());
        verify(userRepository).findByUsername("pepe");
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        when(userRepository.findByEmail("unknown@test.com")).thenReturn(Optional.empty());
        Optional<User> result = adapter.findByEmail("unknown@test.com");
        assertTrue(result.isEmpty());
    }
}
