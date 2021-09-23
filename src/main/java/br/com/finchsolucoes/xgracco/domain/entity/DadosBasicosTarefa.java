package br.com.finchsolucoes.xgracco.domain.entity;


import br.com.finchsolucoes.xgracco.domain.enums.EnumOrigemConclusaoTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumRotulo;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTarefaStatus;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumOrigemConclusaoTarefaConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumRotuloConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumStatusTarefaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author Marcelo Aguiar
 */
@Entity
@Table(name = "DADOSBASICOSTAREFA")
@Audited
@SequenceGenerator(allocationSize = 1, name = "seqDadosBasicosTarefa", sequenceName = "SEQ_DADOSBASICOSTAREFA")
@RelatorioInterface(titulo = "Dados Básicos da Tarefa")
@Data
@Builder
public class DadosBasicosTarefa extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqDadosBasicosTarefa")
    @Column(name = "ID")
    @RelatorioInterface(titulo = "ID", padrao = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    @RelatorioInterface(titulo = "Processo", unwrapped = true)
    private Processo processo;

    @Column(name = "FK_PROCESSO", insertable = false, updatable = false)
    private Long idProcesso;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_INICIO", updatable = false)
    @RelatorioInterface(titulo = "Data Início", padrao = true)
    private Calendar dataInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_AGENDAMENTO")
    @RelatorioInterface(titulo = "Data do Agendamento", padrao = true)
    private Calendar dataAgendamento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_PRAZO_FATAL")
    @RelatorioInterface(titulo = "Data do Prazo Fatal")
    private Calendar dataPrazoFatal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_AGENDAR_PARA")
    @RelatorioInterface(ignore = true)
    private Pessoa agendadoPara;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_REALIZADO_POR")
    @RelatorioInterface(titulo = "Realizado por", unwrapped = true)
    private Pessoa realizadoPor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_CONCLUSAO")
    @RelatorioInterface(titulo = "Data da Conclusão")
    private Calendar dataConclusao;

    @Column(name = "STATUS", nullable = false)
    @Convert(converter = EnumStatusTarefaConverter.class)
    @RelatorioInterface(titulo = "Status", unwrapped = true)
    private EnumStatusTarefa status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_STATUS_FINAL")
    @RelatorioInterface(titulo = "Status Final", unwrapped = true)
    private StatusFinal statusFinal;

    @Column(name = "ROTULO")
    @Convert(converter = EnumRotuloConverter.class)
    @RelatorioInterface(titulo = "Rótulo")
    private EnumRotulo rotulo = EnumRotulo.NENHUM;

    @Column(name = "MEMO")
    @RelatorioInterface(titulo = "Memo")
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_RESPONSAVEL")
    @RelatorioInterface(titulo = "Responsável tarefa", unwrapped = true)
    private Pessoa responsavel;

    @RelatorioInterface(titulo = "Tarefa", unwrapped = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHOTAREFA", referencedColumnName = "ID")
    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JoinColumn(name = "FK_DADOSBASICOSTAREFA_PAI", referencedColumnName = "ID")
    private DadosBasicosTarefa dadosBasicosTarefaPai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSODESPESAS")
    @RelatorioInterface(titulo = "Despesa pai", unwrapped = true)
    private ProcessoDespesas processoDespesas;

    @OneToMany(mappedBy = "dadosBasicosTarefa", fetch = FetchType.LAZY)
    @NotAudited
    private List<DadosBasicosTarefaCampos> dadosBasicosTarefaCampos;

    @Column(name = "ORIGEM_CONCLUSAO")
    @Convert(converter = EnumOrigemConclusaoTarefaConverter.class)
    @RelatorioInterface(titulo = "Origem da Conclusão", unwrapped = true)
    private EnumOrigemConclusaoTarefa origemConclusaoTarefa;

    @Column(name = "LOCAL")
    private String local;

    @Transient
    private String esteiraFila;

    public DadosBasicosTarefa() {

    }

    public DadosBasicosTarefa(Long id) {
        this.id = id;
    }

    public EnumTarefaStatus getStatusTarefa() {
        if (this.dataConclusao != null) {
            return EnumTarefaStatus.CONCLUIDO;
        }

        if (this.dataAgendamento != null) {
            Calendar dataAtual = Calendar.getInstance();
            Calendar dataPrazoTask = Calendar.getInstance();
            dataPrazoTask.setTime(this.dataAgendamento.getTime());

            boolean vencendoNoDia = (dataAtual.get(Calendar.YEAR) == dataPrazoTask.get(Calendar.YEAR)
                    && dataAtual.get(Calendar.DAY_OF_YEAR) == dataPrazoTask.get(Calendar.DAY_OF_YEAR));

            boolean fullDay = dataAtual.get(Calendar.HOUR_OF_DAY) == 0
                    && dataAtual.get(Calendar.MINUTE) == 0
                    && dataAtual.get(Calendar.SECOND) == 0;

            if (fullDay || dataAtual.after(dataPrazoTask)) {  // ATRASO (data atual é depois do prazo)
                return EnumTarefaStatus.ATRASO;
            } else if (vencendoNoDia) {
                return EnumTarefaStatus.VENCENDO_NO_DIA;
            }
        }

        return EnumTarefaStatus.EM_ANDAMENTO;
    }

    public String getStatusTarefaString() {
        return this.getStatusTarefa().getClasseCss();
    }

    @Transient
    public String getDataAgendamentoStr() {
        if (this.dataAgendamento != null) {
            return Util.getDateToString(dataAgendamento, Util.PATTERN_DATA);
        }
        return null;
    }

    @QueryProjection
    public DadosBasicosTarefa(Long id, EnumStatusTarefa status) {
        this.id = id;
        this.status = status;
    }

    @QueryProjection
    public DadosBasicosTarefa(Long id, Calendar dataInicio, Calendar dataAgendamento, Calendar dataPrazoFatal, EnumRotulo rotulo, EnumStatusTarefa status) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataAgendamento = dataAgendamento;
        this.dataPrazoFatal = dataPrazoFatal;
        this.rotulo = rotulo;
        this.status = status;
    }

    @QueryProjection
    public DadosBasicosTarefa(Long id,
                              Calendar dataInicio,
                              Calendar dataAgendamento,
                              Calendar dataConclusao,
                              Calendar dataPrazoFatal,
                              Pessoa responsavel,
                              EnumRotulo rotulo) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataAgendamento = dataAgendamento;
        this.dataConclusao = dataConclusao;
        this.dataPrazoFatal = dataPrazoFatal;
        this.responsavel = responsavel;
        this.rotulo = rotulo;
    }

    @QueryProjection
    public DadosBasicosTarefa(Long id,
                              Calendar dataInicio,
                              Calendar dataAgendamento,
                              Calendar dataConclusao,
                              Calendar dataPrazoFatal,
                              EnumRotulo rotulo,
                              Processo processo,
                              FluxoTrabalhoTarefa fluxoTrabalhoTarefa,
                              Pessoa responsavel,
                              String memo) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataAgendamento = dataAgendamento;
        this.dataConclusao = dataConclusao;
        this.dataPrazoFatal = dataPrazoFatal;
        this.rotulo = rotulo;
        this.processo = processo;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.responsavel = responsavel;
        this.memo = memo;
    }

    @QueryProjection
    public DadosBasicosTarefa(Long id, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Processo processo, Calendar dataInicio, Calendar dataAgendamento, Calendar dataPrazoFatal, Pessoa responsavel, Pessoa realizadoPor, Calendar dataConclusao, EnumRotulo rotulo, String memo, DadosBasicosTarefa dadosBasicosTarefaPai) {
        this.id = id;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.processo = processo;
        this.dataInicio = dataInicio;
        this.dataAgendamento = dataAgendamento;
        this.dataPrazoFatal = dataPrazoFatal;
        this.responsavel = responsavel;
        this.realizadoPor = realizadoPor;
        this.dataConclusao = dataConclusao;
        this.rotulo = rotulo;
        this.memo = memo;
        this.dadosBasicosTarefaPai = dadosBasicosTarefaPai;
    }

    @QueryProjection
    public DadosBasicosTarefa(Long id, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Processo processo, Calendar dataInicio, Calendar dataAgendamento, Calendar dataPrazoFatal, Pessoa responsavel, Pessoa realizadoPor, Calendar dataConclusao, EnumRotulo rotulo, String memo, DadosBasicosTarefa dadosBasicosTarefaPai,  EnumStatusTarefa status) {
        this.id = id;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.processo = processo;
        this.dataInicio = dataInicio;
        this.dataAgendamento = dataAgendamento;
        this.dataPrazoFatal = dataPrazoFatal;
        this.responsavel = responsavel;
        this.realizadoPor = realizadoPor;
        this.dataConclusao = dataConclusao;
        this.rotulo = rotulo;
        this.memo = memo;
        this.dadosBasicosTarefaPai = dadosBasicosTarefaPai;
        this.status = status;
    }

    @QueryProjection
    public DadosBasicosTarefa(Long id, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Processo processo, Calendar dataInicio, Calendar dataAgendamento, Calendar dataPrazoFatal, Pessoa responsavel, Pessoa realizadoPor, Calendar dataConclusao, EnumRotulo rotulo, String memo, DadosBasicosTarefa dadosBasicosTarefaPai, ProcessoDespesas processoDespesas, EnumStatusTarefa status, String local) {
        this.id = id;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.processo = processo;
        this.dataInicio = dataInicio;
        this.dataAgendamento = dataAgendamento;
        this.dataPrazoFatal = dataPrazoFatal;
        this.responsavel = responsavel;
        this.realizadoPor = realizadoPor;
        this.dataConclusao = dataConclusao;
        this.rotulo = rotulo;
        this.memo = memo;
        this.dadosBasicosTarefaPai = dadosBasicosTarefaPai;
        this.processoDespesas = processoDespesas;
        this.status = status;
        this.local = local;
    }

    @QueryProjection
    public DadosBasicosTarefa(Long id, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Processo processo, Calendar dataInicio, Calendar dataAgendamento, Calendar dataPrazoFatal, Pessoa responsavel, Pessoa realizadoPor, Calendar dataConclusao, EnumRotulo rotulo, String memo, DadosBasicosTarefa dadosBasicosTarefaPai, EnumOrigemConclusaoTarefa origemConclusaoTarefa, EnumStatusTarefa status, String local, StatusFinal statusFinal) {
        this.id = id;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.processo = processo;
        this.dataInicio = dataInicio;
        this.dataAgendamento = dataAgendamento;
        this.dataPrazoFatal = dataPrazoFatal;
        this.responsavel = responsavel;
        this.realizadoPor = realizadoPor;
        this.dataConclusao = dataConclusao;
        this.rotulo = rotulo;
        this.memo = memo;
        this.dadosBasicosTarefaPai = dadosBasicosTarefaPai;
        this.origemConclusaoTarefa = origemConclusaoTarefa;
        this.status = status;
        this.local = local;
        this.statusFinal = statusFinal;
    }

    @QueryProjection
    public DadosBasicosTarefa(Long id,
                              Processo processo,
                              Calendar dataInicio,
                              Calendar dataAgendamento,
                              Calendar dataPrazoFatal,
                              EnumStatusTarefa status,
                              Pessoa responsavel,
                              String memo,
                              FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        this.id = id;
        this.processo = processo;
        this.dataInicio = dataInicio;
        this.dataAgendamento = dataAgendamento;
        this.dataPrazoFatal = dataPrazoFatal;
        this.rotulo = rotulo;
        this.status = status;
        this.responsavel = responsavel;
        this.memo = memo;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
    }

    public DadosBasicosTarefa(Processo processo, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Calendar dataAgendamento, Calendar dataPrazoFatal, Pessoa responsavel, EnumRotulo rotulo, String memo, DadosBasicosTarefa dadosBasicosTarefaPai, List<DadosBasicosTarefaCampos> dadosBasicosTarefaCampos, EnumStatusTarefa status) {
        this.processo = processo;
        this.dataAgendamento = dataAgendamento;
        this.dataPrazoFatal = dataPrazoFatal;
        this.rotulo = rotulo;
        this.memo = memo;
        this.responsavel = responsavel;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.dadosBasicosTarefaPai = dadosBasicosTarefaPai;
        this.dadosBasicosTarefaCampos = dadosBasicosTarefaCampos;
        this.status = status;
    }

    @PrePersist
    private void prePersist() {
        this.dataInicio = Calendar.getInstance();
    }

    @PreUpdate
    private void preUpdate() {
        if (Objects.nonNull(this.dataConclusao) && Objects.isNull(this.origemConclusaoTarefa)) {
            this.origemConclusaoTarefa = EnumOrigemConclusaoTarefa.DESCONHECIDO;
        }
    }

    public boolean isConcluida() {
        return (this.getDataConclusao() == null ? false : true);
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return this.getMemo();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        DadosBasicosTarefa that = (DadosBasicosTarefa) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return "DadosBasicosTarefa{" +
                "id=" + id +
                ", dataAgendamento=" + dataAgendamento +
                '}';
    }

    public static DadosBasicosTarefaBuilder builder() {
        return new DadosBasicosTarefaBuilder();
    }

    public static class DadosBasicosTarefaBuilder {
        private Processo processo;
        private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;
        private Calendar dataAgendamento;
        private Calendar dataPrazoFatal;
        private EnumRotulo rotulo = EnumRotulo.NENHUM;
        private String memo;
        private Pessoa responsavel;
        private DadosBasicosTarefa dadosBasicosTarefaPai;
        private List<DadosBasicosTarefaCampos> dadosBasicosTarefaCampos;
        private EnumStatusTarefa status;

        public DadosBasicosTarefaBuilder() {
        }

        public DadosBasicosTarefaBuilder setProcesso(Processo processo) {
            this.processo = processo;
            return this;
        }

        public DadosBasicosTarefaBuilder setFluxoTrabalhoTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
            this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
            return this;
        }

        public DadosBasicosTarefaBuilder setDataAgendamento(Calendar dataAgendamento) {
            this.dataAgendamento = dataAgendamento;
            return this;
        }

        public DadosBasicosTarefaBuilder setDataPrazoFatal(Calendar dataPrazoFatal) {
            this.dataPrazoFatal = dataPrazoFatal;
            return this;
        }


        public DadosBasicosTarefaBuilder setRotulo(EnumRotulo rotulo) {
            this.rotulo = rotulo;
            return this;
        }

        public DadosBasicosTarefaBuilder setMemo(String memo) {
            this.memo = memo;
            return this;
        }

        public DadosBasicosTarefaBuilder setResponsavel(Pessoa responsavel) {
            this.responsavel = responsavel;
            return this;
        }

        public DadosBasicosTarefaBuilder setDadosBasicosTarefaPai(DadosBasicosTarefa dadosBasicosTarefaPai) {
            this.dadosBasicosTarefaPai = dadosBasicosTarefaPai;
            return this;
        }

        public DadosBasicosTarefaBuilder setDadosBasicosTarefaCampos(List<DadosBasicosTarefaCampos> dadosBasicosTarefaCampos) {
            this.dadosBasicosTarefaCampos = dadosBasicosTarefaCampos;
            return this;
        }

        public DadosBasicosTarefaBuilder setStatus(EnumStatusTarefa status) {
            this.status = status;
            return this;
        }

        public DadosBasicosTarefa build() {
            return new DadosBasicosTarefa(processo, fluxoTrabalhoTarefa, dataAgendamento, dataPrazoFatal, responsavel, rotulo, memo, dadosBasicosTarefaPai, dadosBasicosTarefaCampos, status);
        }

    }
}
