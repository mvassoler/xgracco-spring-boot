package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoDataAgendamento;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoIntervaloAgendamento;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoResponsavelAgendamento;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoDataAgendamentoConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoIntervaloAgendamentoConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoResponsavelAgendamentoConverter;
import br.com.finchsolucoes.xgracco.domain.validation.Exists;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Tarefas que serão agendadas para a tarefa/status final.
 *
 * @author rodolpho.couto
 * @since 2.1
 */
@Entity
@Audited
@Table(name = "TAREFA_STATUS_FINAL_AGENDA")
@SequenceGenerator(allocationSize = 1, name = "seqStatusFinalAgendamento", sequenceName = "SEQ_TAREFA_STATUS_FINAL_AGENDA")
@Data
@Builder
public class TarefaStatusFinalAgendamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqStatusFinalAgendamento")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TAREFA_STATUS_FINAL", referencedColumnName = "ID")
    private TarefaStatusFinal tarefaStatusFinal;

    @Exists(message = "{fluxoTrabalhoTarefa.mensagem.erro.tarefaObrigatorio}")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHOTAREFA", referencedColumnName = "ID")
    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    @NotNull
    @Column(name = "ID_TIPO_DATA")
    @Convert(converter = EnumTipoDataAgendamentoConverter.class)
    private EnumTipoDataAgendamento tipoDataAgendamento;

    @NotNull
    @Column(name = "ID_TIPO_RESPONSAVEL")
    @Convert(converter = EnumTipoResponsavelAgendamentoConverter.class)
    private EnumTipoResponsavelAgendamento tipoResponsavelAgendamento;

    @NotNull
    @Column(name = "ID_TIPO_INTERVALO")
    @Convert(converter = EnumTipoIntervaloAgendamentoConverter.class)
    private EnumTipoIntervaloAgendamento tipoIntervaloAgendamento;

    @NotNull
    @Column(name = "INTERVALO")
    private Integer intervalo;

    @NotNull
    @Column(name = "DIAS_UTEIS")
    private boolean diasUteis;

    @Exists(message = "{fluxoTrabalhoTarefa.mensagem.erro.dataCampoObrigatorio}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CAMPO_DATA", referencedColumnName = "ID")
    private Campo campoData;

    @Exists(message = "{fluxoTrabalhoTarefa.mensagem.erro.responsavelCampoObrigatorio}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CAMPO_RESPONSAVEL", referencedColumnName = "ID")
    private Campo campoResponsavel;

    @Exists(message = "{fluxoTrabalhoTarefa.mensagem.erro.responsavelEspecificoObrigatorio}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RESPONSAVEL", referencedColumnName = "ID")
    private Usuario responsavel;

    public TarefaStatusFinalAgendamento() {
    }

    public TarefaStatusFinalAgendamento(Long id) {
        this.id = id;
    }

    @QueryProjection
    public TarefaStatusFinalAgendamento(TarefaStatusFinal tarefaStatusFinal, FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        this.tarefaStatusFinal = tarefaStatusFinal;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
    }

    @QueryProjection
    public TarefaStatusFinalAgendamento(Long id, TarefaStatusFinal tarefaStatusFinal, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, EnumTipoDataAgendamento tipoDataAgendamento, EnumTipoResponsavelAgendamento tipoResponsavelAgendamento, EnumTipoIntervaloAgendamento tipoIntervaloAgendamento, Integer intervalo, boolean diasUteis, Campo campoData, Campo campoResponsavel, Usuario responsavel) {
        this.id = id;
        this.tarefaStatusFinal = tarefaStatusFinal;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.tipoDataAgendamento = tipoDataAgendamento;
        this.tipoResponsavelAgendamento = tipoResponsavelAgendamento;
        this.tipoIntervaloAgendamento = tipoIntervaloAgendamento;
        this.intervalo = intervalo;
        this.diasUteis = diasUteis;
        this.campoData = campoData;
        this.campoResponsavel = campoResponsavel;
        this.responsavel = responsavel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        TarefaStatusFinalAgendamento that = (TarefaStatusFinalAgendamento) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
