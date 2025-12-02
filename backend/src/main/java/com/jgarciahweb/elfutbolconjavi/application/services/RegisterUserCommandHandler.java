package com.jgarciahweb.elfutbolconjavi.application.services;

import com.jgarciahweb.elfutbolconjavi.application.command.RegisterUserCommand;
import com.jgarciahweb.elfutbolconjavi.application.validation.RegisterUserValidator;
import com.jgarciahweb.elfutbolconjavi.domain.mappers.UserMapper;
import com.jgarciahweb.elfutbolconjavi.domain.model.User;
import com.jgarciahweb.elfutbolconjavi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RegisterUserCommandHandler implements CommandHandler<RegisterUserCommand, User> {

    private final UserRepository userRepository;
    private final RegisterUserValidator registerUserValidator;
    private final UserMapper userMapper;

    @Override
    public Mono<User> execute(RegisterUserCommand command) {
        return registerUserValidator.validate(command)
                .then(Mono.fromCallable(() -> userMapper.toDomain(command)))
                .flatMap(userRepository::save);
    }
}
