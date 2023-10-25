package com.github.murilonerdx.skdemoparkapi.entity.enums;

public enum StatusVagaDTO {
    LIVRE, OCUPADA

    ;

    public StatusVaga tDTO(){
        return StatusVaga.valueOf(this.name());
    }
}
