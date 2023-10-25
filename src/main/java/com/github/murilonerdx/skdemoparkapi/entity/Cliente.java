package com.github.murilonerdx.skdemoparkapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.murilonerdx.skdemoparkapi.dto.ClienteResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private String id;
    private String nome;
    private String cpf;
    private String endereco;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCadastro;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;
    private Usuario usuario;

    @JsonIgnore
    public Usuario getUsuario() {
        return usuario;
    }

    @JsonIgnore
    public ClienteResponseDTO toResponseModel(){
        return new ClienteResponseDTO(id, nome, cpf);
    }
}
