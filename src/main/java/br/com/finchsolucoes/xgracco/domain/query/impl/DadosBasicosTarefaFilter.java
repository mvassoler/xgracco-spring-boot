package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTarefaStatus;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Filtro de Dados Basicos Tarefa.
 *
 * @author thiago.fogar
 * @since 4.0
 */
public class DadosBasicosTarefaFilter implements Filter<DadosBasicosTarefa> {

    private Processo processo;
    private Set<FluxoTrabalhoTarefa> listFluxoTrabalhoTarefa;
    private String palavraChave;
    private Boolean possuiAnexo;
    private Pessoa responsavel;
    private Pessoa realizadoPor;
    private Pessoa agendadoPara;
    private DadosBasicosTarefa dadosBasicosTarefaPai;
    private Fila fila;
    private List<String> tags;
    private Boolean semResponsavel;
    private Boolean desconsiderarConfigDatasFila;
    private Boolean retornarTarefasEmAtendimento;
    private Boolean considerarFilasInativas;

    @JsonProperty("tarefa.nome")
    private Set<String> nome;

    @JsonProperty("dataAgendamento")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Calendar dataAgendamento;
    @JsonProperty("dataAgendamento.inicial")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Calendar dataAgendamentoInicial;
    @JsonProperty("dataAgendamento.final")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Calendar dataAgendamentoFinal;

    @JsonProperty("dataConclusao.inicial")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Calendar dataConclusaoInicial;
    @JsonProperty("dataConclusao.final")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Calendar dataConclusaoFinal;

    @JsonProperty("statusTarefa")
    private Set<EnumStatusTarefa> statusTarefa;

    @JsonProperty("status")
    private Set<EnumTarefaStatus> status;

    @JsonProperty("statusFinal.descricao")
    private Set<String> descricaoStatusFinal;

    @JsonProperty("processo.processoUnico")
    private String processoUnico;

    @JsonProperty("processo.id")
    private String processoId;

    @JsonProperty("carteira.id")
    private Long idCarteira;

    public DadosBasicosTarefaFilter() {
    }

    public DadosBasicosTarefaFilter(Pessoa responsavel, Pessoa agendadoPara, Pessoa realizadoPor) {
        this.responsavel = responsavel;
        this.agendadoPara = agendadoPara;
        this.realizadoPor = realizadoPor;
    }

    public DadosBasicosTarefaFilter(Processo processo, Set<FluxoTrabalhoTarefa> listFluxoTrabalhoTarefa, String palavraChave, Boolean possuiAnexo, Set<EnumTarefaStatus> status, Calendar dataAgendamento, Calendar dataAgendamentoInicial, Calendar dataAgendamentoFinal) {
        this.processo = processo;
        this.listFluxoTrabalhoTarefa = listFluxoTrabalhoTarefa;
        this.palavraChave = palavraChave;
        this.possuiAnexo = possuiAnexo;
        this.status = status;
        this.dataAgendamento = dataAgendamento;
        this.dataAgendamentoInicial = dataAgendamentoInicial;
        this.dataAgendamentoFinal = dataAgendamentoFinal;
    }

    private DadosBasicosTarefaFilter(Processo processo, Set<FluxoTrabalhoTarefa> listFluxoTrabalhoTarefa, String palavraChave, Boolean possuiAnexo, Set<String> nome, Pessoa responsavel, Set<EnumTarefaStatus> status, Calendar dataAgendamento, Calendar dataAgendamentoInicial, Calendar dataAgendamentoFinal, Calendar dataConclusaoInicial, Calendar dataConclusaoFinal, DadosBasicosTarefa dadosBasicosTarefaPai, Set<EnumStatusTarefa> statusTarefa, Fila fila, List<String> tags, Boolean semResponsavel, Boolean desconsiderarConfigDatasFila, Boolean retornarTarefasEmAtendimento, Boolean considerarFilasInativas, Set<String> descricaoStatusFinal, String nomeTarefa) {
        this.processo = processo;
        this.listFluxoTrabalhoTarefa = listFluxoTrabalhoTarefa;
        this.palavraChave = palavraChave;
        this.possuiAnexo = possuiAnexo;
        this.nome = nome;
        this.responsavel = responsavel;
        this.status = status;
        this.dataAgendamento = dataAgendamento;
        this.dataAgendamentoInicial = dataAgendamentoInicial;
        this.dataAgendamentoFinal = dataAgendamentoFinal;
        this.dataConclusaoInicial = dataConclusaoInicial;
        this.dataConclusaoFinal = dataConclusaoFinal;
        this.dadosBasicosTarefaPai = dadosBasicosTarefaPai;
        this.statusTarefa = statusTarefa;
        this.fila = fila;
        this.tags = tags;
        this.semResponsavel = semResponsavel;
        this.desconsiderarConfigDatasFila = desconsiderarConfigDatasFila;
        this.retornarTarefasEmAtendimento = retornarTarefasEmAtendimento;
        this.considerarFilasInativas = considerarFilasInativas;
        this.descricaoStatusFinal = descricaoStatusFinal;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public Set<FluxoTrabalhoTarefa> getListFluxoTrabalhoTarefa() {
        return listFluxoTrabalhoTarefa;
    }

    public void setListFluxoTrabalhoTarefa(Set<FluxoTrabalhoTarefa> listFluxoTrabalhoTarefa) {
        this.listFluxoTrabalhoTarefa = listFluxoTrabalhoTarefa;
    }

    public String getPalavraChave() {
        return palavraChave;
    }

    public void setPalavraChave(String palavraChave) {
        this.palavraChave = palavraChave;
    }

    public Boolean getPossuiAnexo() {
        return possuiAnexo;
    }

    public void setPossuiAnexo(Boolean possuiAnexo) {
        this.possuiAnexo = possuiAnexo;
    }

    public Set<EnumTarefaStatus> getStatus() {
        return status;
    }

    public void setStatus(Set<EnumTarefaStatus> status) {
        this.status = status;
    }

    public Calendar getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Calendar dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public Calendar getDataAgendamentoInicial() {
        return dataAgendamentoInicial;
    }

    public void setDataAgendamentoInicial(Calendar dataAgendamentoInicial) {
        this.dataAgendamentoInicial = dataAgendamentoInicial;
    }

    public Calendar getDataAgendamentoFinal() {
        return dataAgendamentoFinal;
    }

    public void setDataAgendamentoFinal(Calendar dataAgendamentoFinal) {
        this.dataAgendamentoFinal = dataAgendamentoFinal;
    }

    public Set<String> getNome() {
        return nome;
    }

    public void setNome(Set<String> nome) {
        this.nome = nome;
    }

    public Pessoa getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Pessoa responsavel) {
        this.responsavel = responsavel;
    }

    public DadosBasicosTarefa getDadosBasicosTarefaPai() {
        return dadosBasicosTarefaPai;
    }

    public void setDadosBasicosTarefaPai(DadosBasicosTarefa dadosBasicosTarefaPai) {
        this.dadosBasicosTarefaPai = dadosBasicosTarefaPai;
    }

    public Set<EnumStatusTarefa> getStatusTarefa() {
        return statusTarefa;
    }

    public void setStatusTarefa(Set<EnumStatusTarefa> statusTarefa) {
        this.statusTarefa = statusTarefa;
    }

    public Fila getFila() {
        return fila;
    }

    public void setFila(Fila fila) {
        this.fila = fila;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Boolean getSemResponsavel() {
        return semResponsavel;
    }

    public void setSemResponsavel(Boolean semResponsavel) {
        this.semResponsavel = semResponsavel;
    }

    public Calendar getDataConclusaoInicial() {
        return dataConclusaoInicial;
    }

    public void setDataConclusaoInicial(Calendar dataConclusaoInicial) {
        this.dataConclusaoInicial = dataConclusaoInicial;
    }

    public Calendar getDataConclusaoFinal() {
        return dataConclusaoFinal;
    }

    public void setDataConclusaoFinal(Calendar dataConclusaoFinal) {
        this.dataConclusaoFinal = dataConclusaoFinal;
    }

    public Pessoa getAgendadoPara() {
        return agendadoPara;
    }

    public void setAgendadoPara(Pessoa agendadoPara) {
        this.agendadoPara = agendadoPara;
    }

    public Pessoa getRealizadoPor() {
        return realizadoPor;
    }

    public void setRealizadoPor(Pessoa realizadoPor) {
        this.realizadoPor = realizadoPor;
    }

    public Boolean getDesconsiderarConfigDatasFila() {
        return desconsiderarConfigDatasFila;
    }

    public void setDesconsiderarConfigDatasFila(Boolean desconsiderarConfigDatasFila) {
        this.desconsiderarConfigDatasFila = desconsiderarConfigDatasFila;
    }

    public Boolean getRetornarTarefasEmAtendimento() {
        return retornarTarefasEmAtendimento != null && retornarTarefasEmAtendimento;
    }

    public void setRetornarTarefasEmAtendimento(Boolean retornarTarefasEmAtendimento) {
        this.retornarTarefasEmAtendimento = retornarTarefasEmAtendimento;
    }

    public Boolean getConsiderarFilasInativas() {
        return considerarFilasInativas != null && considerarFilasInativas;
    }

    public void setConsiderarFilasInativas(Boolean considerarFilasInativas) {
        this.considerarFilasInativas = considerarFilasInativas;
    }

    public Set<String> getDescricaoStatusFinal() {
        return descricaoStatusFinal;
    }

    public void setDescricaoStatusFinal(Set<String> descricaoStatusFinal) {
        this.descricaoStatusFinal = descricaoStatusFinal;
    }

    public String getProcessoUnico() {
        return processoUnico;
    }

    public void setProcessoUnico(String processoUnico) {
        this.processoUnico = processoUnico;
    }

    public String getProcessoId() {
        return processoId;
    }

    public void setProcessoId(String processoId) {
        this.processoId = processoId;
    }

    public void setIdCarteira(Long idCarteira) {
        this.idCarteira = idCarteira;
    }

    public Long getIdCarteira() {
        return idCarteira;
    }

    public DadosBasicosTarefaFilterBuilder builder() {
        return new DadosBasicosTarefaFilterBuilder();
    }

    public class DadosBasicosTarefaFilterBuilder {

        private Processo processo;
        private Set<FluxoTrabalhoTarefa> listFluxoTrabalhoTarefa;
        private String palavraChave;
        private Boolean possuiAnexo;
        private Set<String> nome;
        private Pessoa responsavel;
        private Set<EnumTarefaStatus> status;
        private Calendar dataAgendamento;
        private Calendar dataAgendamentoInicial;
        private Calendar dataAgendamentoFinal;
        private DadosBasicosTarefa dadosBasicosTarefaPai;
        private Set<EnumStatusTarefa> statusTarefa;

        private Calendar dataConclusaoInicial;
        private Calendar dataConclusaoFinal;
        private Fila fila;
        private List<String> tags;
        private Boolean semResponsavel;
        private Boolean desconsiderarConfigDatasFila;
        private Boolean retornarTarefasEmAtendimento;
        private Boolean considerarFilasInativas;
        private Set<String> descricaoStatusFinal;
        private String nomeTarefa;



        public DadosBasicosTarefaFilterBuilder setProcesso(Processo processo) {
            this.processo = processo;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setListFluxoTrabalhoTarefa(Set<FluxoTrabalhoTarefa> listFluxoTrabalhoTarefa) {
            this.listFluxoTrabalhoTarefa = listFluxoTrabalhoTarefa;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setPalavraChave(String palavraChave) {
            this.palavraChave = palavraChave;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setPossuiAnexo(Boolean possuiAnexo) {
            this.possuiAnexo = possuiAnexo;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setNome(Set<String> nome) {
            this.nome = nome;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setResponsavel(Pessoa responsavel) {
            this.responsavel = responsavel;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setStatus(Set<EnumTarefaStatus> status) {
            this.status = status;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setDataAgendamento(Calendar dataAgendamento) {
            this.dataAgendamento = dataAgendamento;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setDataAgendamentoInicial(Calendar dataAgendamentoInicial) {
            this.dataAgendamentoInicial = dataAgendamentoInicial;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setDataAgendamentoFinal(Calendar dataAgendamentoFinal) {
            this.dataAgendamentoFinal = dataAgendamentoFinal;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setDadosBasicosTarefaPai(DadosBasicosTarefa dadosBasicosTarefaPai) {
            this.dadosBasicosTarefaPai = dadosBasicosTarefaPai;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setStatusTarefa(Set<EnumStatusTarefa> statusTarefa) {
            this.statusTarefa = statusTarefa;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setDataConclusaoInicial(Calendar dataConclusaoInicial) {
            this.dataConclusaoInicial = dataConclusaoInicial;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setDataConclusaoFinal(Calendar dataConclusaoFinal) {
            this.dataConclusaoFinal = dataConclusaoFinal;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setFila(Fila fila) {
            this.fila = fila;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setSemResponsavel(Boolean semResponsavel) {
            this.semResponsavel = semResponsavel;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setDesconsiderarConfigDatasFila(Boolean desconsiderarConfigDatasFila) {
            this.desconsiderarConfigDatasFila = desconsiderarConfigDatasFila;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setRetornarTarefasEmAtendimento(Boolean retornarTarefasEmAtendimento) {
            this.retornarTarefasEmAtendimento = retornarTarefasEmAtendimento;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setConsiderarFilasInativas(Boolean considerarFilasInativas) {
            this.considerarFilasInativas = considerarFilasInativas;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setDescricaoStatusFinal(Set<String> descricaoStatusFinal) {
            this.descricaoStatusFinal = descricaoStatusFinal;
            return this;
        }

        public DadosBasicosTarefaFilterBuilder setNomeTarefa(String nomeTarefa) {
            this.nomeTarefa = nomeTarefa;
            return this;
        }


        public DadosBasicosTarefaFilter build() {
            return new DadosBasicosTarefaFilter(
                    this.processo,
                    this.listFluxoTrabalhoTarefa,
                    this.palavraChave,
                    this.possuiAnexo,
                    this.nome,
                    this.responsavel,
                    this.status,
                    this.dataAgendamento,
                    this.dataAgendamentoInicial,
                    this.dataAgendamentoFinal,
                    this.dataConclusaoInicial,
                    this.dataConclusaoFinal,
                    this.dadosBasicosTarefaPai,
                    this.statusTarefa,
                    this.fila,
                    this.tags,
                    this.semResponsavel,
                    this.desconsiderarConfigDatasFila,
                    this.retornarTarefasEmAtendimento,
                    this.considerarFilasInativas,
                    this.descricaoStatusFinal,
                    this.nomeTarefa
            );
        }

    }

}
