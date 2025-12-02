package com.jgarciahweb.elfutbolconjavi.application.command;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class RegisterUserCommand {
    String username;
    String email;
    String password;
    LocalDate birthday;
}
