package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoDataSla;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoDataSlaConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoIntervaloSlaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.bussines.enums.EnumTipoIntervaloSla;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Created by paulomarcon
 */
@Entity
@Table(name = "PAINEL_TAREFA")
@Relation(collectionRelation = "tarefas")
@Audited
@SequenceGenerator(allocationSize = 1, name = "seqPainelTarefa", sequenceName = "SEQ_PAINEL_TAREFA")
@Data
@Builder
@AllArgsConstructor
public class PainelTarefa implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPainelTarefa")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "ID_TIPO_DATA")
    @Convert(converter = EnumTipoDataSlaConverter.class)
    private EnumTipoDataSla tipoDataSla;

    @NotNull
    @Column(name = "ID_TIPO_INTERVALO")
    @Convert(converter = EnumTipoIntervaloSlaConverter.class)
    private EnumTipoIntervaloSla tipoIntervaloSla;

    @NotNull
    @Column(name = "SLA")
    private Integer sla;

    @NotNull
    @Column(name = "PRINCIPAL")
    private boolean principal;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PAINEL", referencedColumnName = "ID")
    private Painel painel;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHOTAREFA", referencedColumnName = "ID")
    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CAMPO_DATA", referencedColumnName = "ID")
    private Campo campoData;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private Tarefa tarefa;

    public PainelTarefa() {
    }

    public PainelTarefa(Long id) {
        this.id = id;
    }

    @QueryProjection
    public PainelTarefa(Long id, EnumTipoDataSla tipoDataSla, EnumTipoIntervaloSla tipoIntervaloSla, Integer sla, boolean principal, Painel painel, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Campo campoData) {
        this.id = id;
        this.tipoDataSla = tipoDataSla;
        this.tipoIntervaloSla = tipoIntervaloSla;
        this.sla = sla;
        this.principal = principal;
        this.painel = painel;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.campoData = campoData;
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
        PainelTarefa that = (PainelTarefa) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
