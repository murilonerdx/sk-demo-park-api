package com.github.murilonerdx.skdemoparkapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.murilonerdx.skdemoparkapi.entity.Usuario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioCreateDTO {
    @Size(min=6, max=24)
    @NotEmpty(message = "Digite um username")
    private String username;

    @Size(min=6, max=24)
    @NotNull(message= "Digite uma senha")
    private String password;
}
