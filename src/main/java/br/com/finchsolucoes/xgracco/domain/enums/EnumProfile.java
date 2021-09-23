package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

/**
 * @author Marcelo Aguiar
 */
public enum EnumProfile implements PersistentEnum {

    FINCH(1, "FINCH"),
    SOLICITACOES(2, "SOLICITACOES"),;

    private final int id;
    private final String nomeProfile;

    EnumProfile(int id, String nomeProfile) {
        this.id = id;
        this.nomeProfile = nomeProfile;
    }

    public static EnumProfile getById(int id) {
        return Stream.of(EnumProfile.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumProfile.class, String.valueOf(id)));
    }

    @Override
    public int getId() {
        return id;
    }

    public String getNomeProfile() {
        return nomeProfile;
    }
}
