package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author guilhermecamargo
 */
public enum EnumStatusPagamentoDespesas implements Serializable{

    NAO_PAGO(0, "NÃO PAGO"),
    PAGO(1, "PAGO");

    private final int id;
    private final String descricao;

    EnumStatusPagamentoDespesas(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumStatusPagamentoDespesas getById(int id) {
        return Stream.of(EnumStatusPagamentoDespesas.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumStatusPagamentoDespesas.class, String.valueOf(id)));
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
