package br.com.finchsolucoes.xgracco.domain.enums;

import java.util.stream.Stream;

public enum SolicitacaoAndamentoLogStatus {
    ENVIADO(1, "Enviado"),
    FINALIZADO(2, "Finalizado"),
    FALHA(3, "Falha ao registrar novos andamentos"),
    FALHA_INTERNA(4, "Falha interna ao recuperar dados da fila");

    private int id;
    private String descricao;

    SolicitacaoAndamentoLogStatus(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static SolicitacaoAndamentoLogStatus getById(int id) {
        return Stream.of(SolicitacaoAndamentoLogStatus.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(SolicitacaoAndamentoLogStatus.class, String.valueOf(id)));
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
