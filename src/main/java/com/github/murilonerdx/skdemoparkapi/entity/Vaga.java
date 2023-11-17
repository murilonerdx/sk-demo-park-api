package com.github.murilonerdx.skdemoparkapi.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.murilonerdx.skdemoparkapi.dto.VagaCreateDTO;
import com.github.murilonerdx.skdemoparkapi.dto.VagaDTO;
import com.github.murilonerdx.skdemoparkapi.entity.enums.StatusVaga;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Vaga {
    @Id
    private String id;
    private String codigo;
    @Enumerated(EnumType.STRING)
    private StatusVaga status;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateCreated = LocalDateTime.now();
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataUpdated;
    private String createBy;
    private String modifyBy;

    @JsonCreator
    public Vaga(@JsonProperty("id") String id,
                @JsonProperty("codigo") String codigo,
                @JsonProperty("status") String status) {
        this.id = id;
        this.codigo = codigo;
        this.status = StatusVaga.fromValue(status);
    }

    public Vaga(String id, String codigo, StatusVaga status, String createBy) {
        this.id = id;
        this.codigo = codigo;
        this.status = status;
        this.createBy = createBy;
    }

    public Vaga(String id, String codigo, StatusVaga status) {
        this.id = id;
        this.codigo = codigo;
        this.status = status;
        this.createBy = LocalDateTime.now().toString();
    }

    public VagaDTO toDTO() {
        return new VagaDTO(id, codigo, status);
    }
}
