package com.jgarciahweb.elfutbolconjavi.apis.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterUserRequestDTO {
    private String username;
    private String email;
    private String password;
    private LocalDate birthday;
}
