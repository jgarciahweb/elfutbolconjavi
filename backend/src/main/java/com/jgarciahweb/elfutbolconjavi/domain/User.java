package com.jgarciahweb.elfutbolconjavi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private RoleEnum role;
    private LocalDate birthday;
    private boolean isAcceptedCookies;
    private boolean isVerified;
}
