package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

/**
 * Created by paulomarcon on 12/04/2016.
 */
public enum EnumFilaTipo implements PersistentEnum {

    DEVOLUCAO(10, "Valor referente a uma tarefa presente na fila de devolução"),
    HIBERNACAO(15, "Valor referente a uma tarefa presente na fila de devolução e hibernada [Data: Follow Up Date]"),
    ATENDIMENTO(50, "Valor referente a uma tarefa presente numa fila de atendimento normal");

    private final int id;
    private final String descricao;

    EnumFilaTipo(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumFilaTipo getById(int id) {
        return Stream.of(EnumFilaTipo.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumFilaTipo.class, String.valueOf(id)));
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
