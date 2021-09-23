package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

public enum EnumTipoDestinatario implements PersistentEnum {

    NENHUM(0, "Nenhum"),
    ESPECIFICO(1, "Específico"),
    OPERACIONAL(2, "Operacional"),
    COORDENADOR(3, "Coordenador");

    private int id;
    private String descricao;

    EnumTipoDestinatario(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumTipoDestinatario getById(int id) {
        return Stream.of(EnumTipoDestinatario.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoDestinatario.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
