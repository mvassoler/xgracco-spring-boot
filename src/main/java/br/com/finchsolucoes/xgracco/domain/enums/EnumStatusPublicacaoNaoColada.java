package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author guilhermecamargo
 */
public enum EnumStatusPublicacaoNaoColada implements Serializable {

    PENDENTE(0, "PENDENTE"),
    IGNORADA(1, "IGNORADA"),
    A_CADASTRAR(2, "A CADASTRAR"),
    TRATADA(3, "TRATADAS"),
    NAO_TRATADA(4, "NÃO TRATADA");


    private final int id;
    private final String descricao;

    EnumStatusPublicacaoNaoColada(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumStatusPublicacaoNaoColada getById(int id) {
        return Stream.of(EnumStatusPublicacaoNaoColada.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumStatusPublicacaoNaoColada.class, String.valueOf(id)));
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
