package com.jgarciahweb.elfutbolconjavi.domain.mappers;

import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import com.jgarciahweb.elfutbolconjavi.domain.model.RoleEnum;
import com.jgarciahweb.elfutbolconjavi.domain.model.User;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        // Arrange
        mapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void toDomain() {
        // Arrange
        RegisterUserCommand command = Instancio.create(RegisterUserCommand.class);

        // Act
        User user = mapper.toDomain(command);

        // Assert
        assertNotNull(user);
        assertEquals(command.getUsername(), user.getUsername());
        assertEquals(command.getEmail(), user.getEmail());
        assertEquals(command.getPassword(), user.getPassword());

        assertEquals(RoleEnum.NORMAL, user.getRole());
        assertFalse(user.isAcceptedCookies());
        assertFalse(user.isVerified());

        assertNotNull(user.getId());
        assertEquals(8, user.getId().length(), "El id generado debe tener 8 caracteres");
    }

    @Test
    void toDomainWithNull() {
        // Arrange
        RegisterUserCommand command = null;

        // Act
        User user = mapper.toDomain(command);

        // Assert
        assertNull(user);
    }

    @Test
    void generateShortId() {
        // Act
        String id1 = mapper.generateShortId();
        String id2 = mapper.generateShortId();

        // Assert
        assertNotNull(id1);
        assertNotNull(id2);
        assertEquals(8, id1.length());
        assertEquals(8, id2.length());
        assertNotEquals(id1, id2);
    }
}
