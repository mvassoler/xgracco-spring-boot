package br.com.finchsolucoes.xgracco.domain.enums;

import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by felipiberdun on 11/01/2017.
 */
@Relation(collectionRelation = "sistemas-virtuais")
public enum EnumSistemaVirtual implements Serializable {

    ELETRONICO(1, "Eletrônico"),
    E_PROC(2, "Eletrônico (e-Proc)"),
    E_SAJ(3, "Eletrônico (e-Saj)"),
    E_THEMMIS(4, "Eletrônico (e-Themmis)"),
    PEA(5, "Eletrônico (PEA)"),
    PJE(6, "Eletrônico (Pje)"),
    SGD(7, "Eletrônico (SGD - 2º Grau)"),
    E_PAT(8, "e-Pat"),
    JEF(9, "JEF"),
    PROJUDI(10, "Projudi"),
    TRF_1(11, "TRF 1"),
    TRF_2(12, "TRF 2"),
    TRF_4(13, "TRF 4"),
    TRF_5(14, "TRF 5"),
    TRF_3(15, "TRF 3"),
    VARA_FEDERAL(16, "Vara Federal (Creta)");

    private final int id;
    private final String descricao;

    EnumSistemaVirtual(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return this.id;
    }

    public static EnumSistemaVirtual getById(int id) {
        return Stream.of(EnumSistemaVirtual.values())
                .filter(enumSistemaVirtual -> enumSistemaVirtual.id == id).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumSistemaVirtual.class, String.valueOf(id)));
    }

    @Override
    public String toString() {
        return descricao;
    }

}
