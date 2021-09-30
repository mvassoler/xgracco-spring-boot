package br.com.finchsolucoes.xgracco.domain.enums;

import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author rodolpho.couto
 */
@Relation(collectionRelation = "tipos-processo")
public enum EnumTipoProcesso implements Serializable {

    ADMINISTRATIVO(1, "Administrativo"),
    JUDICIAL(2, "Judicial");

    private final int id;
    private final String descricao;

    EnumTipoProcesso(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EnumTipoProcesso getById(int id) {
        return Stream.of(EnumTipoProcesso.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoProcesso.class, String.valueOf(id)));
    }

    public static EnumTipoProcesso getByDescricao(String descricao) {
        if (descricao == null){
            return null;
        }
        return Stream.of(EnumTipoProcesso.values())
                .filter(e -> e.getDescricao().trim().toUpperCase().equals(descricao.trim().toUpperCase())).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoProcesso.class, String.valueOf(descricao)));
    }


    public int getId() {
        return id;
    }
}
