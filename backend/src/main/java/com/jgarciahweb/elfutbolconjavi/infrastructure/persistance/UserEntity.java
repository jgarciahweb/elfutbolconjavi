package com.jgarciahweb.elfutbolconjavi.infrastructure.persistance;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collation = "users")
public class UserEntity {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private Instant birthdate;
    private boolean isAcceptedCookies;
    private boolean isVerified;
}
