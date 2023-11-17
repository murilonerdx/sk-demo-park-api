package com.github.murilonerdx.skdemoparkapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.murilonerdx.skdemoparkapi.dto.EstacionamentoResponseDTO;
import com.github.murilonerdx.skdemoparkapi.repository.ClienteVagaProjection;
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
public class ClienteVaga implements ClienteVagaProjection{
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

    public ClienteVagaProjection toProjection(){
        return new ClienteVagaProjection() {
            @Override
            public String getPlaca() {
                return placa;
            }

            @Override
            public String getMarca() {
                return marca;
            }

            @Override
            public String getModelo() {
                return modelo;
            }

            @Override
            public String getCor() {
                return  cor;
            }

            @Override
            public String getClienteCpf() {
                return cliente.getCpf();
            }

            @Override
            public String getRecibo() {
                return recibo;
            }

            @Override
            public LocalDateTime getDataEntrada() {
                return dataEntrada;
            }

            @Override
            public LocalDateTime getDataSaida() {
                return dataSaida;
            }

            @Override
            public String getVagaCodigo() {
                return vaga.getCodigo();
            }

            @Override
            public BigDecimal getValor() {
                return valor;
            }

            @Override
            public BigDecimal getDesconto() {
                return desconto;
            }
        };
    }

    @Override
    public String getClienteCpf() {
        return null;
    }

    @Override
    public String getVagaCodigo() {
        return null;
    }
}
