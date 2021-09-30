package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by felipiberdun on 11/01/2017.
 */
public enum EnumTipoIndiceEconomico implements Serializable {

    TJ(1, Arrays.asList(EnumArea.CIVEL, EnumArea.AMBIENTAL, EnumArea.CONSUMIDOR, EnumArea.CRIMINAL, EnumArea.REGULATORIO, EnumArea.TRIBUTARIA)),
    TRT(2, Arrays.asList(EnumArea.TRABALHISTA)),
    SELIC(3, Arrays.asList(EnumArea.values())),
    TR(4, Arrays.asList(EnumArea.values())),
    COMERCIO_MERCADO(5, Arrays.asList(EnumArea.values())),
    ENCOGE_CJF(6, Arrays.asList(EnumArea.values())),
    TJ_INVERSO(7, Arrays.asList(EnumArea.values())),
    VARIACAO_MENSAL(8, Arrays.asList(EnumArea.values()));


    private final int id;
    private final List<EnumArea> areas;

    EnumTipoIndiceEconomico(int id, List<EnumArea> areas) {
        this.id = id;
        this.areas = areas;
    }

    public int getId() {
        return this.id;
    }

    public List<EnumArea> getAreas() {
        return areas;
    }

    public static EnumTipoIndiceEconomico getById(int id) {
        return Stream.of(EnumTipoIndiceEconomico.values())
                .filter(enumTipoIndiceEconomico -> enumTipoIndiceEconomico.id == id).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoIndiceEconomico.class, String.valueOf(id)));
    }

}
