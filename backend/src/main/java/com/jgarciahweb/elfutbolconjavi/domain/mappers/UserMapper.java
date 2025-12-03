package com.jgarciahweb.elfutbolconjavi.domain.mappers;

import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import com.jgarciahweb.elfutbolconjavi.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper()
public interface UserMapper {

    @Mapping(target = "id", expression = "java(generateShortId())")
    @Mapping(target = "role", constant = "NORMAL")
    @Mapping(target = "isAcceptedCookies", constant = "false")
    @Mapping(target = "isVerified", constant = "false")
    User toDomain(RegisterUserCommand command);

    default String generateShortId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
