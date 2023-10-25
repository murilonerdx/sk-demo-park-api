package com.github.murilonerdx.skdemoparkapi.dto;

import com.github.murilonerdx.skdemoparkapi.entity.enums.StatusVaga;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class VagaDTO {
    private String id;
    private String codigo;
    @Enumerated(EnumType.STRING)
    private StatusVaga status;

    public VagaDTO(String id, String codigo, StatusVaga status) {
        this.id = id;
        this.codigo = codigo;
        this.status = status;
    }
}
