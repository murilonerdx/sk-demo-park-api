package com.github.murilonerdx.skdemoparkapi.dto;

import com.github.murilonerdx.skdemoparkapi.entity.ClienteVaga;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteVagaCreateDTO {
    @NotBlank(message = "O campo recibo é obrigatório")
    private String recibo;
    @Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "O campo placa deve estar no formato AAA-0000")
    @NotBlank(message = "O campo placa é obrigatório")
    private String placa;
    @NotBlank(message = "O campo marca é obrigatório")
    private String marca;
    @NotBlank(message = "O campo modelo é obrigatório")
    private String modelo;
    @NotBlank(message = "O campo cor é obrigatório")
    private String cor;
    @Size(min=11, max = 11)
    @CPF(message = "O campo CPF é obrigatório")
    private String clienteCpf;

    public ClienteVaga toModel(){
        return new ClienteVaga(this.recibo, this.placa, this.marca, this.modelo, this.cor);
    }
}
