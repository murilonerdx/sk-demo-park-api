package com.github.murilonerdx.skdemoparkapi.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum StatusVaga {
    LIVRE("LIVRE"),
    OCUPADA("OCUPADA"),
    RESERVADA("RESERVADA"),
    MANUTENCAO("MANUTENCAO"),
    INTERDITADA("INTERDITADA"),
    BLOQUEADA("BLOQUEADA"),
    DESATIVADA("DESATIVADA"),
    EXCLUIDA("EXCLUIDA"),
    INDISPONIVEL("INDISPONIVEL");

    private final String value;

    StatusVaga(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static StatusVaga fromValue(String value) {
        return Arrays.stream(StatusVaga.values())
                .filter(e -> e.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status inválido: " + value));
    }
}
