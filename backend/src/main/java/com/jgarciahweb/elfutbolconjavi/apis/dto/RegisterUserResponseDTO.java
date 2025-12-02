package com.jgarciahweb.elfutbolconjavi.apis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserResponseDTO {
    private boolean success;
    private String message;
}
