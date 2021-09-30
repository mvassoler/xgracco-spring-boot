package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

/**
 * Created by jordano on 23/05/16.
 */
public enum EnumTipoContagemDias implements PersistentEnum {

    DIAS_CORRIDOS(0, "Dias Corridos"),
    DIAS_UTEIS(1, "Dias Uteis");

    private final int id;
    private final String descricao;

    EnumTipoContagemDias(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumTipoContagemDias getById(int id) {
        return Stream.of(EnumTipoContagemDias.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoContagemDias.class, String.valueOf(id)));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
