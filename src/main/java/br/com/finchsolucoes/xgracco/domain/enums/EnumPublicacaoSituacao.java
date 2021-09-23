package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author Jordano
 */
public enum EnumPublicacaoSituacao implements Serializable {

    PUBLICACAO_CONCLUIDA(1, "Concluida"),
    PUBLICACAO_FINALIZADA(2, "Finalizada"),
    PUBLICACAO_DEVOLVIDA(3, "Devolvida");

    private final int id;
    private final String descricao;

    EnumPublicacaoSituacao(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumPublicacaoSituacao getById(int id) {
        return Stream.of(EnumPublicacaoSituacao.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumPublicacaoSituacao.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
