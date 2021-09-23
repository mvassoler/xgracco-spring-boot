package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author marceloaguiar
 */
public enum EnumTarefaStatus implements Serializable {

    CONCLUIDO(1, "CONCLUIDO", 5),
    VENCENDO_NO_DIA(2, "VENCENDO", 1),
    HIBERNANDO(3, "HIBERNANDO", 4),
    ATRASO(4, "ATRASO", 0),
    EM_ANDAMENTO(5, "ANDAMENTO", 3);

    private final int id;
    private final String classeCss;
    private final int ordenacao;

    EnumTarefaStatus(int id, String classeCss, int ordenacao) {
        this.id = id;
        this.classeCss = classeCss;
        this.ordenacao = ordenacao;
    }

    public static EnumTarefaStatus getById(int id) {
        return Stream.of(EnumTarefaStatus.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTarefaStatus.class, String.valueOf(id)));
    }

    public static EnumTarefaStatus getByDescricao(final String descricao) {
        if (descricao == null){
            return null;
        }
        return Stream.of(EnumTarefaStatus.values())
                .filter(enumTarefaStatus -> enumTarefaStatus.getClasseCss().trim().toUpperCase().equals(descricao.trim().toUpperCase())).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTarefaStatus.class, descricao));
    }

    public int getId() {
        return id;
    }

    public String getClasseCss() {
        return classeCss;
    }

    public int getOrdenacao() {
        return ordenacao;
    }
}
