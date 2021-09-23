/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

/**
 * @author enedycordeiro
 */
public enum EnumTipoCampo implements PersistentEnum {

    TIPO_CAMPO_DATA(1),
    TIPO_CAMPO_LISTA(2),
    TIPO_CAMPO_NUMERO(3),
    TIPO_CAMPO_TEXTO(4),
    TIPO_CAMPO_VALOR(5),
    TIPO_CAMPO_HORA(6),
    TIPO_CAMPO_UPLOAD(7),
    TIPO_CAMPO_PESSOA(8),
    TIPO_CAMPO_TEXTAREA(9),
    TIPO_CAMPO_SQL(10),
    TIPO_CAMPO_LISTA_DINAMICA(11),
    TIPO_CAMPO_CAIXA_VERIFICACAO(12);

    private final int id;

    EnumTipoCampo(int id) {
        this.id = id;
    }

    public static EnumTipoCampo getById(int id) {
        return Stream.of(EnumTipoCampo.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoCampo.class, String.valueOf(id)));
    }

    @Override
    public int getId() {
        return id;
    }

}
