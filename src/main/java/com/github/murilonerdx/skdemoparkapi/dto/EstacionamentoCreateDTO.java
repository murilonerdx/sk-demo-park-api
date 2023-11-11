package com.github.murilonerdx.skdemoparkapi.dto;

import com.github.murilonerdx.skdemoparkapi.entity.ClienteVaga;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstacionamentoCreateDTO {
    @NotBlank
    @Size(min = 8, max = 8)
    @Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "A placa do veículo deve seguir o padrão 'XXX-0000'")
    private String placa;
    @NotBlank
    private String marca;
    @NotBlank
    private String modelo;
    @NotBlank
    private String cor;
    @NotBlank
    @Size(min = 11, max = 11)
    @CPF
    private String clienteCpf;

    public ClienteVaga toModel() {
        return new ClienteVaga(
                this.placa,
                this.marca,
                this.modelo,
                this.cor,
                this.clienteCpf
        );
    }
}
