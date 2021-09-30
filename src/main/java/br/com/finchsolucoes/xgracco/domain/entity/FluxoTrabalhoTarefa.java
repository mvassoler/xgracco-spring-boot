package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Audited
@Table(name = "FLUXO_TRABALHO_TAREFA")
@Relation(collectionRelation = "fluxo-trabalho-tarefas")
@SequenceGenerator(allocationSize = 1, name = "seqFluxoTrabalhoTarefa", sequenceName = "SEQ_FLUXOTRABALHOTAREFA")
@RelatorioInterface(titulo = "Fluxo Trabalho Tarefa")
@Data
@Builder
@AllArgsConstructor
public class FluxoTrabalhoTarefa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqFluxoTrabalhoTarefa")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHO", referencedColumnName = "ID")
    @RelatorioInterface(titulo = "Fluxo de Trabalho")
    private FluxoTrabalho fluxoTrabalho;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_TAREFA", referencedColumnName = "ID")
    @RelatorioInterface(titulo = "Tarefa", unwrapped = true)
    private Tarefa tarefa;

    @Column(name = "ATIVO")
    private Boolean ativo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NOTIFICACAO_FK_USUARIO", referencedColumnName = "ID")
    @RelatorioInterface(titulo = "Usuário", unwrapped = true)
    private Usuario usuario;

    @Column(name = "NOTIFICACAO_CRIACAO")
    private Boolean notificacaoCriacao;

    @Column(name = "NOTIFICACAO_VENCIMENTO")
    private Boolean notificacaoVencimento;

    @Column(name = "NOTIFICACAO_VENCIMENTO_INTERVALO")
    private Integer notificacaoIntervalo;

    @NotAudited
    @Valid
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fluxoTrabalhoTarefa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TarefaStatusFinal> statusFinais;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fluxoTrabalhoTarefa")
    private List<DadosBasicosTarefa> dadosBasicosTarefas;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fluxoTrabalhoTarefa")
    private List<EsteiraTarefa> esteiras;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fluxoTrabalhoTarefa")
    private List<FilaTarefa> filas;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fluxoTrabalhoTarefa")
    private List<CarteiraEventoTarefa> eventosCarteira;

    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "fluxoTrabalhoTarefas")
    private List<ContratoClausula> clausulasContrato;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fluxoTrabalhoTarefas")
    private List<FluxoTrabalho> fluxosTrabalho;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fluxoTrabalhoTarefa")
    private List<ModeloAgendamento> modelosAgendamento;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fluxoTrabalhoTarefa")
    private List<PainelTarefa> paineis;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fluxoTrabalhoTarefa")
    private List<TarefaStatusFinalAgendamento> agendamentosStatusFinal;

    public FluxoTrabalhoTarefa() {
    }

    @QueryProjection
    public FluxoTrabalhoTarefa(final Long id) {
        this.id = id;
    }

    @QueryProjection
    public FluxoTrabalhoTarefa(Long id, Tarefa tarefa) {
        this.id = id;
        this.tarefa = tarefa;
    }

    @QueryProjection
    public FluxoTrabalhoTarefa(Long id, FluxoTrabalho fluxoTrabalho, Tarefa tarefa) {
        this.id = id;
        this.fluxoTrabalho = fluxoTrabalho;
        this.tarefa = tarefa;
    }

    @QueryProjection
    public FluxoTrabalhoTarefa(Long id, FluxoTrabalho fluxoTrabalho, Tarefa tarefa,
                               Boolean notificacaoCriacao, Boolean notificacaoVencimento,
                               Integer notificacaoIntervalo, Usuario usuario) {
        this.id = id;
        this.fluxoTrabalho = fluxoTrabalho;
        this.tarefa = tarefa;
        this.notificacaoCriacao = notificacaoCriacao;
        this.notificacaoVencimento = notificacaoVencimento;
        this.notificacaoIntervalo = notificacaoIntervalo;
        this.usuario = usuario;
    }

    @JsonIgnore
    public void setEventosCarteira(List<CarteiraEventoTarefa> eventosCarteira) {
        this.eventosCarteira = eventosCarteira;
    }

    @JsonProperty
    public List<ContratoClausula> getClausulasContrato() {
        return clausulasContrato;
    }

    @JsonIgnore
    public void setClausulasContrato(List<ContratoClausula> clausulasContrato) {
        this.clausulasContrato = clausulasContrato;
    }

    @JsonProperty
    public List<ModeloAgendamento> getModelosAgendamento() {
        return modelosAgendamento;
    }

    @JsonIgnore
    public void setModelosAgendamento(List<ModeloAgendamento> modelosAgendamento) {
        this.modelosAgendamento = modelosAgendamento;
    }

    @JsonProperty
    public List<PainelTarefa> getPaineis() {
        return paineis;
    }

    @JsonIgnore
    public void setPaineis(List<PainelTarefa> paineis) {
        this.paineis = paineis;
    }

    @JsonProperty
    public List<TarefaStatusFinalAgendamento> getAgendamentosStatusFinal() {
        return agendamentosStatusFinal;
    }

    @JsonIgnore
    public void setAgendamentosStatusFinal(List<TarefaStatusFinalAgendamento> agendamentosStatusFinal) {
        this.agendamentosStatusFinal = agendamentosStatusFinal;
    }

    @JsonIgnore
    @Transient
    public boolean isPossuiDependencias() {
        return (CollectionUtils.isNotEmpty(getEsteiras())
                || CollectionUtils.isNotEmpty(getFilas())
                || CollectionUtils.isNotEmpty(getEventosCarteira())
                || CollectionUtils.isNotEmpty(getClausulasContrato())
                || CollectionUtils.isNotEmpty(getModelosAgendamento())
                || CollectionUtils.isNotEmpty(getPaineis())
                || CollectionUtils.isNotEmpty(getAgendamentosStatusFinal())
                || CollectionUtils.isNotEmpty(getDadosBasicosTarefas())) && getAtivo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        FluxoTrabalhoTarefa fluxoTrabalhoTarefa = (FluxoTrabalhoTarefa) o;
        return Objects.equals(this.getId(), fluxoTrabalhoTarefa.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "FluxoTrabalhoTarefa{" +
                "id=" + id +
                ", fluxoTrabalho=" + fluxoTrabalho +
                ", tarefa=" + tarefa +
                '}';
    }
}
