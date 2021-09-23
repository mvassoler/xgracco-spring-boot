package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author guilhermecamargo
 */
public enum EnumOrigemProcesso implements Serializable {

    LEGADO_DESCONHECIDO(0, "Legado/Desconhecido"),
    API(1, "API"),
    TUTELA(2, "Tutela"),
    IMPORTACAO(3, "Importação"),
    PUBLICACAO_NAO_COLADA(4, "Publicação não colada"),
    PRE_CADASTRO_PROCESSO(5, "Pré-Cadastro de Processo");

    private final int id;
    private final String descricao;

    EnumOrigemProcesso(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumOrigemProcesso getById(int id) {
        return Stream.of(EnumOrigemProcesso.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumOrigemProcesso.class, String.valueOf(id)));
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