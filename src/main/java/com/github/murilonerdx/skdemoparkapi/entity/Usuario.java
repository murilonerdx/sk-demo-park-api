package com.github.murilonerdx.skdemoparkapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.murilonerdx.skdemoparkapi.dto.UsuarioDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    @Id
    private String id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_CUSTOMER;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime dataUpdated;
    private String createBy;
    private String modifyBy;

    public Usuario(String id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.dataUpdated = LocalDateTime.now();
    }

    public enum Role{
        ROLE_ADMIN, ROLE_CUSTOMER
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
        this.dataUpdated = LocalDateTime.now();
    }

    @JsonIgnore
    public UsuarioDTO toDTO(){
        return new UsuarioDTO(this.id, this.username, null, this.role);
    }
}
