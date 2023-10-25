package com.github.murilonerdx.skdemoparkapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.murilonerdx.skdemoparkapi.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private String id;
    private String username;

    @JsonIgnore
    private String password;

    private Usuario.Role role;

    public UsuarioDTO(String s1, String s2) {
        this.username = s1;
        this.password = s2;
    }

    public Usuario toModel(){
        return new Usuario(id, username, password, role);
    }

}
