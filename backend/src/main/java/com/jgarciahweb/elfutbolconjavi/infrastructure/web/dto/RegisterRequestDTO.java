package com.jgarciahweb.elfutbolconjavi.infrastructure.web.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
}
