package com.github.murilonerdx.skdemoparkapi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.murilonerdx.skdemoparkapi.entity.Cliente;
import com.github.murilonerdx.skdemoparkapi.entity.Vaga;
import com.github.murilonerdx.skdemoparkapi.entity.enums.StatusVaga;
import com.github.murilonerdx.skdemoparkapi.entity.enums.StatusVagaDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class VagaCreateDTO {
    @JsonProperty("codigo")
    private String codigo;
    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private StatusVagaDTO status;

    public Vaga toModel(String createBy) {
        return new Vaga(null, this.codigo, this.status.tDTO(), createBy);
    }

    @JsonCreator
    public VagaCreateDTO(
                @JsonProperty("codigo") String codigo,
                @JsonProperty("status") StatusVagaDTO status) {
        this.codigo = codigo;
        this.status = status;
    }
}
