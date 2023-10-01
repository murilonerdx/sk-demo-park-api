package com.github.murilonerdx.skdemoparkapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.murilonerdx.skdemoparkapi.entity.Usuario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {
    @NotEmpty(message = "Digite um username")
    private String username;

    @NotNull(message= "Digita uma senha")
    private String password;
}
