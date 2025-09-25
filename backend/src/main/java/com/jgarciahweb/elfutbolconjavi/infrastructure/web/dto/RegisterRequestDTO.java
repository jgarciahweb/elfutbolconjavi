package com.jgarciahweb.elfutbolconjavi.infrastructure.web.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
    private LocalDate birthday;
    private boolean isAcceptedCookies;
}
