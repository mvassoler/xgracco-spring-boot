package br.com.finchsolucoes.xgracco.domain.enums;

import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by Jordano on 19/04/2018.
 */
@Relation(collectionRelation = "reversoes-provisao")
public enum EnumReversaoProvisao implements Serializable {

    TERCEIRO_INTERESSADO(1, "Terceiro Interessado", Boolean.TRUE),
    BBB_PARTE_ATIVA(2, "BBB Parte Ativa", Boolean.TRUE),
    FCVS(3, "FCVS", Boolean.TRUE),
    ARQUIVADO_EM_DEFINITIVO(4, "Arquivado Em Definitivo", Boolean.TRUE),
    ACORDO(5, "Acordo", Boolean.TRUE),
    REMOTO(6, "Remoto", Boolean.TRUE),
    RECOVERY(7, "Recovery", Boolean.FALSE),
    BBB_EXCLUIDO(8, "BBB Excluído", Boolean.TRUE),
    PASSIVO_VINCULADO(9, "Passivo Vinculado", Boolean.TRUE),
    NAO_LOCALIZADO(10, "Não Localizado", Boolean.TRUE),
    JEC(11, "JEC", Boolean.FALSE),
    ILIQUIDO_PERMANENTE(12, "Ilíquido Permanente", Boolean.TRUE),
    USUCAPIAO(13, "Usucapião", Boolean.TRUE),
    JEF(14, "JEF", Boolean.FALSE),
    ARQUIVADO(15, "Arquivado", Boolean.TRUE),
    OBRIGACAO_FAZER(16, "Obrigação De Fazer", Boolean.TRUE),
    PROGNOSTICO_INCONCLUSIVO(17, "Prognóstico inconclusivo / sem prognóstico", Boolean.TRUE);

    private final int id;
    private final String descricao;
    private final Boolean reversao;


    EnumReversaoProvisao(int id, String descricao, Boolean reversao) {
        this.id = id;
        this.descricao = descricao;
        this.reversao = reversao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getReversao() {
        return reversao;
    }

    public static EnumReversaoProvisao getById(int id) {
        return Stream.of(EnumReversaoProvisao.values())
                .filter(instancia -> instancia.id == id).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumReversaoProvisao.class, String.valueOf(id)));
    }

    public static EnumReversaoProvisao getByDescricao(final String descricao) {
        return Stream.of(EnumReversaoProvisao.values())
                .filter(enumReversaoProvisao -> enumReversaoProvisao.getDescricao().equals(descricao)).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumReversaoProvisao.class, descricao));
    }

    @Override
    public String toString() {
        return descricao;
    }
}
