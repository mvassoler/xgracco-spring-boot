package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumPeriodicidadeJuros;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoIndiceEconomico;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumPeriodicidadeJurosConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoIndiceEconomicoConverter;
import br.com.finchsolucoes.xgracco.domain.validation.Unique;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Relation(collectionRelation = "indices-economicos")
@Table(name = "INDICEECONOMICO")
@SequenceGenerator(allocationSize = 1, name = "seqIndiceEconomico", sequenceName = "SEQ_INDICEECONOMICO")
@Audited
@Unique("descricao")
@Data
@Builder
@AllArgsConstructor
public class IndiceEconomico implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqIndiceEconomico")
    @Column(name = "ID")
    private Long id;

    @Size(max = 100)
    @NotBlank
    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "FK_TIPO_IND_ECO")
    @Convert(converter = EnumTipoIndiceEconomicoConverter.class)
    @NotNull
    private EnumTipoIndiceEconomico tipoIndiceEconomico;

    @Column(name = "FK_PER_JUR_COR")
    @Convert(converter = EnumPeriodicidadeJurosConverter.class)
    @NotNull
    private EnumPeriodicidadeJuros periodoJurosCorrecao;

    @Column(name = "CONTEMPLA_ALTERACAO_MOEDA")
    private boolean contemplaAlteracaoMoeda;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "indiceEconomico")
    @NotAudited
    private List<IndiceEconomicoVar> indicesEconomicosVar;

    @Transient
    private LogAuditoria logAuditoria;

    public IndiceEconomico() {
    }

    public IndiceEconomico(Long id) {
        this.id = id;
    }

    @QueryProjection
    public IndiceEconomico(Long id, String descricao, EnumPeriodicidadeJuros periodoJurosCorrecao, EnumTipoIndiceEconomico tipoIndiceEconomico) {
        this.id = id;
        this.descricao = descricao;
        this.periodoJurosCorrecao = periodoJurosCorrecao;
        this.tipoIndiceEconomico = tipoIndiceEconomico;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        IndiceEconomico that = (IndiceEconomico) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return descricao;
    }
}
