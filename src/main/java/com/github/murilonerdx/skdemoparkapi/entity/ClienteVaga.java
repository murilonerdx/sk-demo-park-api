package com.github.murilonerdx.skdemoparkapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.murilonerdx.skdemoparkapi.dto.EstacionamentoResponseDTO;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteVaga {
    @Id
    private String id;
    private String recibo;
    private String placa;
    private String marca;
    private String modelo;
    private String cor;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

    private LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime dataUpdated;
    private String createBy;
    private String modifyBy;
    private Cliente cliente;
    private Vaga vaga;
    private BigDecimal valor;
    private BigDecimal desconto;



    public ClienteVaga(String placa, String marca, String modelo, String cor, String cpf) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.dateCreated = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        this.cliente = new Cliente();
        this.cliente.setCpf(cpf);
    }

    public EstacionamentoResponseDTO toDTO() {
        return new EstacionamentoResponseDTO(
                this.recibo,
                this.placa,
                this.marca,
                this.modelo,
                this.cor
        );
    }

}
