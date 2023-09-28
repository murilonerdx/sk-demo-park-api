package com.github.murilonerdx.skdemoparkapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private String id;
    private String username;
    private String password;
    private Role role;
    private LocalDateTime dateCreated;
    private LocalDateTime dataUpdated;
    private String createBy;
    private String modifyBy;

    public enum Role{
        ROLE_ADMIN, ROLE_CUSTOMER
    }
}
