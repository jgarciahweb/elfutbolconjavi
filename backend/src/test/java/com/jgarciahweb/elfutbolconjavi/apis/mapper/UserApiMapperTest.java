package com.jgarciahweb.elfutbolconjavi.apis.mapper;

import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import com.jgarciahweb.elfutbolconjavi.apis.dto.RegisterUserRequestDTO;
import com.jgarciahweb.elfutbolconjavi.apis.dto.RegisterUserResponseDTO;
import com.jgarciahweb.elfutbolconjavi.domain.model.User;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserApiMapperTest {

    private UserApiMapper mapper;

    @BeforeEach
    void setUp() {
        // Arrange
        mapper = Mappers.getMapper(UserApiMapper.class);
    }

    @Test
    void toCommand() {
        // Arrange
        RegisterUserRequestDTO requestDTO = Instancio.create(RegisterUserRequestDTO.class);

        // Act
        RegisterUserCommand command = mapper.toCommand(requestDTO);

        // Assert
        assertNotNull(command);
        assertEquals(requestDTO.getUsername(), command.getUsername());
        assertEquals(requestDTO.getEmail(), command.getEmail());
        assertEquals(requestDTO.getPassword(), command.getPassword());
    }

    @Test
    void toCommandWithNull() {
        // Arrange
        RegisterUserRequestDTO requestDTO = null;

        // Act
        RegisterUserCommand command = mapper.toCommand(requestDTO);

        // Assert
        assertNull(command);
    }

    @Test
    void toResponse() {
        // Arrange
        User user = Instancio.create(User.class);

        // Act
        RegisterUserResponseDTO responseDTO = mapper.toResponse(user);

        // Assert
        assertNotNull(responseDTO);
        assertTrue(responseDTO.isSuccess());
        assertEquals("Usuario registrado con éxito", responseDTO.getMessage());
    }

    @Test
    void toResponseWithNull() {
        // Arrange
        User user = null;

        // Act
        RegisterUserResponseDTO responseDTO = mapper.toResponse(user);

        // Assert
        assertNull(responseDTO);
    }

    @Test
    void toErrorResponse() {
        // Arrange
        String errorMessage = "Error de prueba";

        // Act
        RegisterUserResponseDTO responseDTO = mapper.toErrorResponse(errorMessage);

        // Assert
        assertNotNull(responseDTO);
        assertFalse(responseDTO.isSuccess());
        assertEquals(errorMessage, responseDTO.getMessage());
    }
}
