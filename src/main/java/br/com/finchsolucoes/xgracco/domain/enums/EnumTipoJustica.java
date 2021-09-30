package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;
import org.springframework.hateoas.server.core.Relation;

import java.util.stream.Stream;

/**
 * Created by felipiberdun on 24/10/2016.
 */
@Relation(collectionRelation = "tipos-justica")
public enum EnumTipoJustica implements PersistentEnum {

    ARBITRAL(1, "Arbitral"),
    CRIMINAL(2, "Criminal"),
    DO_TRABALHO(3, "Do Trabalho"),
    ESTADUAL(4, "Estadual"),
    FEDERAL(5, "Federal");

    private final int id;
    private final String descricao;

    EnumTipoJustica(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumTipoJustica getById(int id) {
        return Stream.of(EnumTipoJustica.values())
                .filter(tipoJustica -> tipoJustica.getId() == id).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoJustica.class, String.valueOf(id)));
    }

    public static EnumTipoJustica getByDescricao(final String descricao) {
        if (descricao == null){
            return null;
        }
        return Stream.of(EnumTipoJustica.values())
                .filter(tipoJustica -> tipoJustica.getDescricao().trim().toUpperCase().equals(descricao.trim().toUpperCase())).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoJustica.class, descricao));
    }

    @Override
    public int getId() {
        return this.id;
    }

    public String getDescricao() {
        return descricao;
    }
}
