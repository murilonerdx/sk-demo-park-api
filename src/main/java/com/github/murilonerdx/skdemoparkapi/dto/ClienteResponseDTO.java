package com.github.murilonerdx.skdemoparkapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {
    private String id;
    private String nome;
    private String cpf;


    public ClienteDTO toModel(){
        return new ClienteDTO(id, nome, cpf, null, null, null, null);
    }
}
