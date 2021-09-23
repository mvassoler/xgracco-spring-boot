package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author guilhermecamargo
 */
public enum EnumPublicacaoNaoColadaAcao implements Serializable {

    TRATAMENTO(0, "Aguardando tratamentos"),
    PENDENTE(1, "Pendente"),
    ENCAMINHOU_CADASTRO(2, "Encaminhada para cadastro"),
    VINCULADA(3, "Vinculada à um processo"),
    GEROU_CADASTRO(4, "Gerou novo cadastro"),
    IGNORADA(4, "Publicação ignorada");


    private final int id;
    private final String descricao;

    EnumPublicacaoNaoColadaAcao(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumPublicacaoNaoColadaAcao getById(int id) {
        return Stream.of(EnumPublicacaoNaoColadaAcao.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumPublicacaoNaoColadaAcao.class, String.valueOf(id)));
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

