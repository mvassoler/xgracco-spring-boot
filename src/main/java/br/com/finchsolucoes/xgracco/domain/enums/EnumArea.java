package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;
import org.springframework.hateoas.server.core.Relation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by felipiberdun on 26/10/2016.
 */
@Relation(collectionRelation = "areas")
public enum EnumArea implements PersistentEnum {

    CIVEL(1, "Cível", Arrays.asList(
            EnumTipoJustica.ARBITRAL,
            EnumTipoJustica.CRIMINAL,
            EnumTipoJustica.ESTADUAL,
            EnumTipoJustica.DO_TRABALHO,
            EnumTipoJustica.FEDERAL)),
    CRIMINAL(2, "Criminal", Arrays.asList(
            EnumTipoJustica.ARBITRAL,
            EnumTipoJustica.CRIMINAL,
            EnumTipoJustica.ESTADUAL,
            EnumTipoJustica.FEDERAL)),
    TRABALHISTA(3, "Trabalhista", Arrays.asList(
            EnumTipoJustica.ARBITRAL,
            EnumTipoJustica.CRIMINAL,
            EnumTipoJustica.DO_TRABALHO,
            EnumTipoJustica.FEDERAL)),
    TRIBUTARIA(4, "Tributária", Arrays.asList(
            EnumTipoJustica.ARBITRAL,
            EnumTipoJustica.CRIMINAL,
            EnumTipoJustica.ESTADUAL,
            EnumTipoJustica.FEDERAL)),
    AMBIENTAL(5, "Ambiental", Arrays.asList(
            EnumTipoJustica.ARBITRAL,
            EnumTipoJustica.CRIMINAL,
            EnumTipoJustica.DO_TRABALHO,
            EnumTipoJustica.ESTADUAL,
            EnumTipoJustica.FEDERAL)),
    CONSUMIDOR(6, "Consumidor", Arrays.asList(
            EnumTipoJustica.ARBITRAL,
            EnumTipoJustica.CRIMINAL,
            EnumTipoJustica.DO_TRABALHO,
            EnumTipoJustica.ESTADUAL,
            EnumTipoJustica.FEDERAL)),
    REGULATORIO(7, "Regulatório", Arrays.asList(
            EnumTipoJustica.ARBITRAL,
            EnumTipoJustica.CRIMINAL,
            EnumTipoJustica.DO_TRABALHO,
            EnumTipoJustica.ESTADUAL,
            EnumTipoJustica.FEDERAL));

    private final int id;
    private final String descricao;
    private final List<EnumTipoJustica> tiposJustica;

    EnumArea(int id, String descricao, List<EnumTipoJustica> tiposJustica) {
        this.id = id;
        this.descricao = descricao;
        this.tiposJustica = tiposJustica;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<EnumTipoJustica> getTiposJustica() {
        return this.tiposJustica;
    }

    public static EnumArea getById(final int id) {
        return Stream.of(EnumArea.values())
                .filter(enumArea -> enumArea.getId() == id).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumArea.class, String.valueOf(id)));
    }

    public static EnumArea getByDescricao(final String descricao) {
        if (descricao == null){
            return null;
        }
        return Stream.of(EnumArea.values())
                .filter(enumArea -> enumArea.getDescricao().trim().toUpperCase().equals(descricao.trim().toUpperCase())).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumArea.class, descricao));
    }

    @Override
    public String toString() {
        return descricao;
    }
}
