/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author paulomarcon
 */
public enum EnumStatusDevolucaoFila implements Serializable {

    INCORRETO(0, "Incorreto"),
    CORRETO(1, "Correto");

    private final int id;
    private final String descricao;

    EnumStatusDevolucaoFila(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumStatusDevolucaoFila getById(int id) {
        return Stream.of(EnumStatusDevolucaoFila.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumStatusDevolucaoFila.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
