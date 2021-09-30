package br.com.finchsolucoes.xgracco.domain.enums;

import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by felipiberdun on 11/01/2017.
 */
@Relation(collectionRelation = "ufs")
public enum EnumUf implements Serializable {

    AC(1, 19, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.E_SAJ, EnumSistemaVirtual.PJE)),
    AL(2, 26, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.E_SAJ, EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_5, EnumSistemaVirtual.VARA_FEDERAL, EnumSistemaVirtual.PJE)),
    AM(3, 24, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.E_SAJ, EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_1, EnumSistemaVirtual.PJE)),
    AP(4, 25, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.ELETRONICO, EnumSistemaVirtual.PJE)),
    BA(5, 15, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.E_SAJ, EnumSistemaVirtual.PJE, EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_1)),
    CE(6, 27, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.E_SAJ, EnumSistemaVirtual.PJE, EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_5, EnumSistemaVirtual.VARA_FEDERAL)),
    DF(7, 21, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.PJE, EnumSistemaVirtual.TRF_1)),
    ES(8, 22, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.PJE)),
    GO(9, 6, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.PJE, EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_1)),
    MA(10, 10, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.PJE, EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_1)),
    MG(11, 13, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.PJE, EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_1)),
    MS(12, 20, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.E_SAJ, EnumSistemaVirtual.PJE)),
    MT(13, 16, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.PEA, EnumSistemaVirtual.PJE, EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_1)),
    PA(14, 12, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_1, EnumSistemaVirtual.PJE)),
    PB(15, 9, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.PJE, EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_5, EnumSistemaVirtual.VARA_FEDERAL)),
    PE(16, 23, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.PJE, EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_5, EnumSistemaVirtual.VARA_FEDERAL)),
    PI(17, 11, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_1, EnumSistemaVirtual.PJE)),
    PR(18, 14, new BigDecimal(12),
            Arrays.asList(EnumSistemaVirtual.E_SAJ, EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_4, EnumSistemaVirtual.PJE)),
    RJ(19, 2, new BigDecimal(12),
            Arrays.asList(EnumSistemaVirtual.ELETRONICO, EnumSistemaVirtual.PJE)),
    RN(20, 8, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.E_SAJ, EnumSistemaVirtual.PJE, EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_5, EnumSistemaVirtual.VARA_FEDERAL)),
    RO(21, 4, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.PJE, EnumSistemaVirtual.SGD, EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_1)),
    RR(22, 3, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_1, EnumSistemaVirtual.PJE)),
    RS(23, 7, new BigDecimal(12),
            Arrays.asList(EnumSistemaVirtual.E_THEMMIS, EnumSistemaVirtual.PJE, EnumSistemaVirtual.TRF_4)),
    SC(24, 17, new BigDecimal(12),
            Arrays.asList(EnumSistemaVirtual.E_SAJ, EnumSistemaVirtual.TRF_4, EnumSistemaVirtual.PJE)),
    SE(25, 18, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.ELETRONICO, EnumSistemaVirtual.TRF_5, EnumSistemaVirtual.PJE)),
    SP(26, 1, new BigDecimal(18),
            Arrays.asList(EnumSistemaVirtual.E_SAJ, EnumSistemaVirtual.E_PAT, EnumSistemaVirtual.JEF, EnumSistemaVirtual.TRF_3, EnumSistemaVirtual.PJE)),
    TO(27, 5, new BigDecimal(7),
            Arrays.asList(EnumSistemaVirtual.E_PROC, EnumSistemaVirtual.PROJUDI, EnumSistemaVirtual.TRF_1, EnumSistemaVirtual.PJE));

    private final int id;
    private final BigDecimal icms;
    private final int codigoExterno;
    private final List<EnumSistemaVirtual> sistemas;

    EnumUf(int id, int codigoExterno, BigDecimal icms, List<EnumSistemaVirtual> sistemas) {
        this.id = id;
        this.icms = icms;
        this.codigoExterno = codigoExterno;
        this.sistemas = sistemas;
    }

    public int getId() {
        return this.id;
    }

    public List<EnumSistemaVirtual> getSistemas() {
        return this.sistemas;
    }

    public static EnumUf getById(int id) {
        return Stream.of(EnumUf.values())
                .filter(enumUf -> enumUf.getId() == id).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumUf.class, String.valueOf(id)));
    }

}
