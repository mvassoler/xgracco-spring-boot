package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.entity.Agenda;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PublicacaoRequestView implements Serializable {

    private static final long serialVersionUID = 7675783761724069100L;

    private String textoPublicacao;
    private String dataPublicacao;
    private String horaPublicacao;
    private String origemPublicacao;
    private String idUnicoProcesso;
    private String idTask;
    private String caseExecutionId;
    private Long idPublicacaoIntegracao;
    private String agendamentoAutomatico;
    private Long idProcesso;
    private Long idDadosBasicosTarefa;

    private List<PublicacaoAgendamento> agendamentos;

    public String getTextoPublicacao() {
        return textoPublicacao;
    }

    public void setTextoPublicacao(String textoPublicacao) {
        this.textoPublicacao = textoPublicacao;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getOrigemPublicacao() {
        return origemPublicacao;
    }

    public void setOrigemPublicacao(String origemPublicacao) {
        this.origemPublicacao = origemPublicacao;
    }

    public String getIdUnicoProcesso() {
        return idUnicoProcesso;
    }

    public void setIdUnicoProcesso(String idUnicoProcesso) {
        this.idUnicoProcesso = idUnicoProcesso;
    }

    public String getIdTask() {
        return idTask;
    }

    public void setIdTask(String idTask) {
        this.idTask = idTask;
    }

    public String getCaseExecutionId() {
        return caseExecutionId;
    }

    public void setCaseExecutionId(String caseExecutionId) {
        this.caseExecutionId = caseExecutionId;
    }

    public List<PublicacaoAgendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<PublicacaoAgendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public String getHoraPublicacao() {
        return horaPublicacao;
    }

    public void setHoraPublicacao(String horaPublicacao) {
        this.horaPublicacao = horaPublicacao;
    }

    public Long getIdPublicacaoIntegracao() {
        return idPublicacaoIntegracao;
    }

    public void setIdPublicacaoIntegracao(Long idPublicacaoIntegracao) {
        this.idPublicacaoIntegracao = idPublicacaoIntegracao;
    }

    public String getAgendamentoAutomatico() {
        return agendamentoAutomatico;
    }

    public void setAgendamentoAutomatico(String agendamentoAutomatico) {
        this.agendamentoAutomatico = agendamentoAutomatico;
    }

    public Long getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Long idProcesso) {
        this.idProcesso = idProcesso;
    }

    public void fromAgenda(Agenda agendamento) {
        this.dataPublicacao = agendamento.getDataAgendamentoFormatada();
        this.agendamentos = new ArrayList<>();

        PublicacaoAgendamento pAgendamento = new PublicacaoAgendamento();

        String dataAgendamento = Util.getDateToString(agendamento.getDataAgendamento(), Util.PATTERN_DATA);
        String dataFinal = Util.getDateToString(agendamento.getDataFinal(), Util.PATTERN_DATA);
        String horaAgendamento = Util.getDateToString(agendamento.getDataAgendamento(), Util.PATTERN_HORA);
        String horaFinal = Util.getDateToString(agendamento.getDataFinal(), Util.PATTERN_HORA);

        pAgendamento.setDataAgendamento(dataAgendamento);
        pAgendamento.setDataFinal(dataFinal);
        pAgendamento.setHoraAgendamento(horaAgendamento);
        pAgendamento.setHoraFinal(horaFinal);

        pAgendamento.setAcao(agendamento.getCaseExecutionId());
        pAgendamento.setResponsavel(agendamento.getIdResponsavel());
        pAgendamento.setRotulo(agendamento.getRotulo());
        pAgendamento.setMemo(agendamento.getDescricao());

        this.agendamentos.add(pAgendamento);
    }

    public Long getIdDadosBasicosTarefa() {
        return idDadosBasicosTarefa;
    }

    public void setIdDadosBasicosTarefa(Long idDadosBasicosTarefa) {
        this.idDadosBasicosTarefa = idDadosBasicosTarefa;
    }
}
