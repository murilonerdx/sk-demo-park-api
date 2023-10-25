package com.github.murilonerdx.skdemoparkapi.dto;

import com.github.murilonerdx.skdemoparkapi.entity.Cliente;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCreateDTO {
    @NotNull
    @Size(min = 5, max = 100)
    private String nome;
    @CPF
    @Size(min=11, max = 11)
    private String cpf;
    private String endereco;

    public Cliente toModel(){
        return new Cliente(null, nome, cpf, endereco, LocalDateTime.now(), null, null);
    }

}
