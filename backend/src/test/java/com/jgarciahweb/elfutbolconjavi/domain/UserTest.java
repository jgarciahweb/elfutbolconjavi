package com.jgarciahweb.elfutbolconjavi.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserWithBuilder() {
        User user = User.builder()
                .id("1")
                .username("javi")
                .email("test@test.com")
                .password("123")
                .build();

        assertEquals("javi", user.getUsername());
    }

    @Test
    void shouldUseAllArgsConstructor() {
        User user = new User("1", "javi", "test@test.com", "123", RoleEnum.NORMAL, LocalDate.now(), true, false);
        assertEquals("test@test.com", user.getEmail());
    }
}
