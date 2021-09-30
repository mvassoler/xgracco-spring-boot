package br.com.finchsolucoes.xgracco.domain.enums;

import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by jordano on 28/04/16.
 */
@Relation(collectionRelation = "regioes")
public enum EnumRegiaoUf implements Serializable {

    NORTE(1, Arrays.asList(EnumUf.AC, EnumUf.AM, EnumUf.AP, EnumUf.PA, EnumUf.RO, EnumUf.RR, EnumUf.TO)),
    NORDESTE(2, Arrays.asList(EnumUf.AL, EnumUf.BA, EnumUf.CE, EnumUf.MA, EnumUf.PB, EnumUf.PE, EnumUf.PI, EnumUf.RN, EnumUf.SE)),
    CENTRO_OESTE(3, Arrays.asList(EnumUf.DF, EnumUf.GO, EnumUf.MS, EnumUf.MT)),
    SUDESTE(4, Arrays.asList(EnumUf.ES, EnumUf.MG, EnumUf.RJ, EnumUf.SP)),
    SUL(5, Arrays.asList(EnumUf.PR, EnumUf.RS, EnumUf.SC));

    private final int id;
    private final List<EnumUf> ufs;

    EnumRegiaoUf(int id, List<EnumUf> ufs) {
        this.id = id;
        this.ufs = ufs;
    }

    public static EnumRegiaoUf getById(int id) {
        return Stream.of(EnumRegiaoUf.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumRegiaoUf.class, String.valueOf(id)));
    }

    public int getId() {
        return this.id;
    }

    public List<EnumUf> getUfs() {
        return this.ufs;
    }
}
