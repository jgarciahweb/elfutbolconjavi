package com.jgarciahweb.elfutbolconjavi.apis.mapper;

import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import com.jgarciahweb.elfutbolconjavi.domain.model.User;
import com.jgarciahweb.elfutbolconjavi.apis.dto.RegisterUserRequestDTO;
import com.jgarciahweb.elfutbolconjavi.apis.dto.RegisterUserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserApiMapper {

    RegisterUserCommand toCommand(RegisterUserRequestDTO requestDTO);

    @Mapping(target = "success", constant = "true")
    @Mapping(target = "message", constant = "Usuario registrado con éxito")
    RegisterUserResponseDTO toResponse(User user);

    default RegisterUserResponseDTO toErrorResponse(String errorMessage) {
        return new RegisterUserResponseDTO(false, errorMessage);
    }
}
