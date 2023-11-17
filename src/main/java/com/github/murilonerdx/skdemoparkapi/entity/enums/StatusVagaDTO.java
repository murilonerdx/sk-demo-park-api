package com.github.murilonerdx.skdemoparkapi.entity.enums;

public enum StatusVagaDTO {
    LIVRE("LIVRE"), OCUPADA("OCUPADA")

    ;

    private String value;

    StatusVagaDTO(String value){
        this.value = value;
    }
    public StatusVaga tDTO(){
        return StatusVaga.fromValue(this.name());
    }
}
