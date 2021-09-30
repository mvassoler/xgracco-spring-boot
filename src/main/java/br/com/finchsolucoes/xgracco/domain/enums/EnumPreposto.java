/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author saraveronez
 */
public enum EnumPreposto implements Serializable {

    FINCH(1, "FINCH"),
    CLIENTE(2, "CLIENTE"),
    SEM_PREPOSTO(3, "NÃO HÁ PREPOSTO");

    private final int id;
    private final String descricao;

    EnumPreposto(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumPreposto getById(int id) {
        return Stream.of(EnumPreposto.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumPreposto.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
