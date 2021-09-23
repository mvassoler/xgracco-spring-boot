package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumRotulo;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoAgenda;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumRotuloConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "AGENDAMENTO")
@SequenceGenerator(allocationSize = 1, name = "seqAgenda", sequenceName = "SEQ_AGENDA")
@RelatorioInterface(titulo = "Agenda Simplificada")
@Data
@Builder
@AllArgsConstructor
public class Agenda extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 270292324623029339L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqAgenda")
    @RelatorioInterface(ignore = true)
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Responsável", padrao = true, unwrapped = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_RESPONSAVEL", insertable = false, updatable = false)
    private Pessoa responsavel;

    @Column(name = "FK_RESPONSAVEL")
    @RelatorioInterface(ignore = true)
    private Long idResponsavel;

    @Column(name = "FK_CASEEXECUTION", length = 100)
    @RelatorioInterface(ignore = true)
    private String caseExecutionId;

    @Column(name = "ROTULO")
    @Convert(converter = EnumRotuloConverter.class)
    @RelatorioInterface(titulo = "Rótulo", padrao = true)
    private EnumRotulo rotulo = EnumRotulo.NENHUM;

    @RelatorioInterface(titulo = "Nome da Tarefa", padrao = true, label = "Nome da Tarefa")
    @Column(name = "TEXTO")
    private String texto;

    @RelatorioInterface(titulo = "Memo", label = "Memo")
    @Column(name = "MEMO")
    private String memo;

    @RelatorioInterface(titulo = "Data de Agendamento")
    //@NotNull(message = "cadastroAgenda.campo.data.obrigatorio")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATAAGENDAMENTO")
    private Calendar dataAgendamento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATAFINAL")
    private Calendar dataFinal;

    @Column(name = "REALIZADO")
    private Boolean realizado = false;

    @Column(name = "ALERTA")
    private Boolean alerta = false;

    @Column(name = "LOCAL")
    private String local;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DADOSBASICOSTAREFA")
    private DadosBasicosTarefa dadosBasicosTarefa;

    @Transient
    private EnumTipoAgenda tipo;

    @Transient
    private String descricao;

    @Transient
    private String dataAgendamentoFormatada;

    @Transient
    private String dataFinalFormatada;

    @Transient
    private String horaAgendamentoFormatada;

    @Transient
    private String horaFinalFormatada;

    @Transient
    private Long idProcesso;

    @Transient
    @JsonIgnore(false)
    @RelatorioInterface(ignore = true)
    private Processo processo;

    @Transient
    private String idTarefa;

    @Transient
    private Long idCarteira;

    @Transient
    private String parteContraria;

    @Transient
    private String parteInteressada;

    @Transient
    private String numeroProcesso;

    public Agenda() {
    }

    public Agenda(Long id) {
        this.id = id;
    }


    @QueryProjection
    public Agenda(Long id, Pessoa responsavel, String caseExecutionId, EnumRotulo rotulo, String texto, String memo, Calendar dataAgendamento, Calendar dataFinal, Boolean realizado, Boolean alerta, String local, Processo processo) {
        this.id = id;
        this.responsavel = responsavel;
        this.caseExecutionId = caseExecutionId;
        this.rotulo = rotulo;
        this.texto = texto;
        this.memo = memo;
        this.dataAgendamento = dataAgendamento;
        this.dataFinal = dataFinal;
        this.realizado = realizado;
        this.alerta = alerta;
        this.local = local;
        this.processo = processo;
    }

    public Long getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(Long idCarteira) {
        this.idCarteira = idCarteira;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return id.toString() + " " + texto;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getResponsavel() {
        return responsavel;
    }



    public String getDataAgendamentoFormatada() {
        if (dataAgendamentoFormatada == null) {
            dataAgendamentoFormatada = Util.getDateToString(dataAgendamento, Util.PATTERN_DATA);
        }
        return dataAgendamentoFormatada;
    }

    public void setDataAgendamentoFormatada(String dataAgendamentoFormatada) {
        this.dataAgendamentoFormatada = dataAgendamentoFormatada;
    }

    public String getDataFinalFormatada() {
        if (dataFinalFormatada == null) {
            return Util.getDateToString(dataFinal, Util.PATTERN_DATA);
        }
        return dataFinalFormatada;
    }

    public void setDataFinalFormatada(String dataFinalFormatada) {
        this.dataFinalFormatada = dataFinalFormatada;
    }

    public String getHoraAgendamentoFormatada() {
        if (horaAgendamentoFormatada == null) {
            horaAgendamentoFormatada = Util.getDateToString(dataAgendamento, Util.PATTERN_HORA);
        }

        return horaAgendamentoFormatada;
    }

    public void setHoraAgendamentoFormatada(String horaAgendamentoFormatada) {
        this.horaAgendamentoFormatada = horaAgendamentoFormatada;
    }

    public String getHoraFinalFormatada() {
        if (horaFinalFormatada == null) {
            horaFinalFormatada = Util.getDateToString(dataFinal, Util.PATTERN_HORA);
        }
        return horaFinalFormatada;
    }

    public void setHoraFinalFormatada(String horaFinalFormatada) {
        this.horaFinalFormatada = horaFinalFormatada;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Agenda agenda = (Agenda) o;
        return Objects.equals(this.getId(), agenda.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
