/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

/**
 * @author maiconcarraro
 */
public enum EnumTipoModelo implements PersistentEnum {

    MODELO(1, "Modelo"),
    CABECALHO(2, "Cabeçalho"),
    RODAPE(3, "Rodapé");

    private final int id;
    private final String descricao;

    EnumTipoModelo(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumTipoModelo getById(int id) {
        return Stream.of(EnumTipoModelo.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoModelo.class, String.valueOf(id)));
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
