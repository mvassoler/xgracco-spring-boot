package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author Jordano
 */
public enum EnumPublicacao implements Serializable {

    PUBLICACAO_IDENTIFICADOR(1, "#publicacao"),
    PUBLICACAO_NAO_ENCONTRADA(2, "As publicações informadas, não foram encontradas"),
    PUBLICACAO_ERRO_DEVOLUCAO(3, "Houveram erros na devolução da publicação"),
    PUBLICACAO(4, "PUBLICAÇÃO");

    private final int id;
    private final String descricao;

    EnumPublicacao(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumPublicacao getById(int id) {
        return Stream.of(EnumPublicacao.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumPublicacao.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
