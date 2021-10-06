package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumStatusTarefaConverter;
import br.com.finchsolucoes.xgracco.core.validation.Exists;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Status Finais relacionados à tarefa.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Entity
@Audited
@Table(name = "TAREFA_STATUS_FINAL")
//@Unique({"tarefa", "statusFinal", "status"})
@Relation(collectionRelation = "status-finais")
@SequenceGenerator(allocationSize = 1, name = "seqTarefaStatusFinal", sequenceName = "SEQ_TAREFA_STATUS_FINAL")
@Data
@Builder
@AllArgsConstructor
public class TarefaStatusFinal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqTarefaStatusFinal")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHOTAREFA", referencedColumnName = "ID")
    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    @Exists(message = "{fluxoTrabalhoTarefa.mensagem.erro.statusFinalObrigatorio}")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_STATUS_FINAL", referencedColumnName = "ID")
    private StatusFinal statusFinal;

    @NotNull
    @Column(name = "ID_STATUS")
    @Convert(converter = EnumStatusTarefaConverter.class)
    private EnumStatusTarefa status;

    @Exists(message = "{fluxoTrabalhoTarefa.mensagem.erro.gormularioObrigatorio}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FORMULARIO", referencedColumnName = "ID")
    private Formulario formulario;

    @Valid
    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefaStatusFinal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TarefaStatusFinalAgendamento> agendamentos;

    public TarefaStatusFinal() {
    }

    public TarefaStatusFinal(Long id) {
        this.id = id;
    }

    @QueryProjection
    public TarefaStatusFinal(Long id, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, StatusFinal statusFinal, EnumStatusTarefa status, Formulario formulario) {
        this.id = id;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.statusFinal = statusFinal;
        this.status = status;
        this.formulario = formulario;
    }

    public TarefaStatusFinal(FluxoTrabalhoTarefa fluxoTrabalhoTarefa, StatusFinal statusFinal, EnumStatusTarefa status, Formulario formulario, List<TarefaStatusFinalAgendamento> agendamentos) {
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.statusFinal = statusFinal;
        this.status = status;
        this.formulario = formulario;
        this.agendamentos = agendamentos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        TarefaStatusFinal that = (TarefaStatusFinal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}