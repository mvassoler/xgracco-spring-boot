package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

public enum EnumStatusPagamentoHonorario implements Serializable {

    NAO_PAGO(0, "NÃO PAGO"),
    PAGO(1, "PAGO");

    private final int id;
    private final String descricao;

    EnumStatusPagamentoHonorario(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumStatusPagamentoHonorario getById(int id) {
        return Stream.of(EnumStatusPagamentoHonorario.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumStatusPagamentoHonorario.class, String.valueOf(id)));
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
