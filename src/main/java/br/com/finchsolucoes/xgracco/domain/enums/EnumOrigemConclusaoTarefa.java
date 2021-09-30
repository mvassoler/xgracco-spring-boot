package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

public enum EnumOrigemConclusaoTarefa implements Serializable {

    SEM_ORIGEM(0, "Legado"),
    TUTELA(1, "Tutela"),
    FILA(2, "Fila"),
    CONCLUSAO_AUTOMATICA(3, "Conclusão automática"),
    REAGENDAMENTO(4, "Reagendamento"),
    INTEGRACAO_API(5, "Integração/Api"),
    DESCONHECIDO(6, "Desconhecido"),
    BAIXA_TAREFAS_LOTE(7, "Baixa de Tarefas em Lote");

    private Integer id;
    private String descricao;

    EnumOrigemConclusaoTarefa(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumOrigemConclusaoTarefa getById(Integer id) {
        return Stream.of(EnumOrigemConclusaoTarefa.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumOrigemConclusaoTarefa.class, String.valueOf(id)));
    }

    public Integer getId() {
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
