package com.jgarciahweb.elfutbolconjavi.infrastructure.persistance;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collation = "users")
public class UserEntity {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
}
