package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

/**
 * Created by felipiberdun on 27/10/2016.
 */
public enum EnumTipoDepositoJuizo implements PersistentEnum {

    ENTRADA(1),
    SAIDA(2);

    private int id;

    EnumTipoDepositoJuizo(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public static EnumTipoDepositoJuizo getById(int id) {
        return Stream.of(EnumTipoDepositoJuizo.values())
                .filter(enumTipoDepositoJuizo -> enumTipoDepositoJuizo.getId() == id).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoDepositoJuizo.class, String.valueOf(id)));
    }

}
