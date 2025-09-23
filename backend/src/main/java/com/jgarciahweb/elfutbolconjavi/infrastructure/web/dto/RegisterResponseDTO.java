package com.jgarciahweb.elfutbolconjavi.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponseDTO {
    private boolean success;
    private String message;
}
