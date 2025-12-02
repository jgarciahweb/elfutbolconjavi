package com.jgarciahweb.elfutbolconjavi.domain.mappers;

import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import com.jgarciahweb.elfutbolconjavi.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "id", expression = "java(generateShortId())")
    @Mapping(target = "role", constant = "NORMAL")
    @Mapping(target = "isAcceptedCookies", constant = "false")
    @Mapping(target = "isVerified", constant = "false")
    @Mapping(target = "password", expression = "java(encodePassword(command.getPassword()))")
    public abstract User toDomain(RegisterUserCommand command);

    protected String generateShortId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    protected String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
