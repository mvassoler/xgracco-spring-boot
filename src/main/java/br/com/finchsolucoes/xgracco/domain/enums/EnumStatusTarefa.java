package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

public enum EnumStatusTarefa implements PersistentEnum {

    CUMPRIDO(1, "Cumprido"),
    NAO_CUMPRIDO(2, "Não cumprido"),
    PENDENTE(3, "Pendente"),
    HIBERNADA(4, "Hibernada"),
    DEVOLVIDA(5, "Devolvida");


    private final int id;
    private final String descricao;

    EnumStatusTarefa(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumStatusTarefa getById(int id) {
        return Stream.of(EnumStatusTarefa.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumStatusTarefa.class, String.valueOf(id)));
    }

    @Override
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
