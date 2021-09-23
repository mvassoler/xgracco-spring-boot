package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by felipiberdun on 11/01/2017.
 */
public enum EnumTratamentoPessoa implements Serializable {

    DR(1),
    DRA(2),
    SR(3),
    SRA(4);

    private final int id;

    EnumTratamentoPessoa(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static EnumTratamentoPessoa getById(int id) {
        return Stream.of(EnumTratamentoPessoa.values())
                .filter(enumTratamentoPessoa -> enumTratamentoPessoa.id == id).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTratamentoPessoa.class, String.valueOf(id)));
    }

}
