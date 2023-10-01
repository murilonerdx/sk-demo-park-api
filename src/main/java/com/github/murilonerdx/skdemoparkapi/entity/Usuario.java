package com.github.murilonerdx.skdemoparkapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.murilonerdx.skdemoparkapi.dto.UsuarioDTO;
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
    private Role role = Role.ROLE_CUSTOMER;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime dataUpdated;
    private String createBy;
    private String modifyBy;

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
