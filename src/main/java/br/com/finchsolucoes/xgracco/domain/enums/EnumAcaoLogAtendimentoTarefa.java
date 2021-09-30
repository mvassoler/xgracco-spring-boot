package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Enum responsável por determinar a ação tomada por usuário em uma tarefa atendida por uma fila
 *
 * @author paulo.marcon
 * @since 5.4.0
 */
public enum EnumAcaoLogAtendimentoTarefa implements Serializable {

    ATENDENDO(1),
    CONCLUSAO(2),
    CONCLUSAO_TUTELA(3),
    DEVOLUCAO(4),
    LIBERACAO_DEVOLUCAO(5),
    HIBERNACAO(6),
    CONCLUSAO_EXTERNA(7);

    private final int id;

    EnumAcaoLogAtendimentoTarefa(int id) {
        this.id = id;
    }

    public static EnumAcaoLogAtendimentoTarefa getById(int id) {
        return Stream.of(EnumAcaoLogAtendimentoTarefa.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumSexo.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
