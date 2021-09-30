package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

/**
 * @author Marcelo Aguiar
 */
public enum EnumProcessoTipoPerda implements PersistentEnum {

    INEXISTENTE(1, "Possível"),
    PERDA_POSSIVEL(2, "Provável"),
    PERDA_PROVAVEL(3, "Remoto");

    private final int id;
    private final String descricao;

    EnumProcessoTipoPerda(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumProcessoTipoPerda getById(int id) {
        return Stream.of(EnumProcessoTipoPerda.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumProcessoTipoPerda.class, String.valueOf(id)));
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
