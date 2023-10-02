package com.github.murilonerdx.skdemoparkapi.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDTO {
    @Size(min=6, max=24)
    @NotNull(message="A senha é muito curta")
    private String newPassword;

    @Size(min=6, max=24)
    private String password;
}
