package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTarefaStatus;

import java.util.Date;

/**
 *
 * @author marceloaguiar
 */
public class TarefasFilaView {
    private Long idFila;
    private String numeroProcesso;
    private String tarefa;
    private String parteContraria;
    private String prazoInicialFinal;
    private String status;
    private String emAtendimentoPor;
    private EnumTarefaStatus statusTarefa;
    private Date dataAgendamento;

    public Long getIdFila() {
        return idFila;
    }

    public void setIdFila(Long idFila) {
        this.idFila = idFila;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public String getParteContraria() {
        return parteContraria;
    }

    public void setParteContraria(String parteContraria) {
        this.parteContraria = parteContraria;
    }

    public String getPrazoInicialFinal() {
        return prazoInicialFinal;
    }

    public void setPrazoInicialFinal(String prazoInicialFinal) {
        this.prazoInicialFinal = prazoInicialFinal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmAtendimentoPor() {
        return emAtendimentoPor;
    }

    public void setEmAtendimentoPor(String emAtendimentoPor) {
        this.emAtendimentoPor = emAtendimentoPor;
    }

    public String getTarefa() {
        return tarefa;
    }

    public void setTarefa(String tarefa) {
        this.tarefa = tarefa;
    }

    public EnumTarefaStatus getStatusTarefa() {
        return statusTarefa;
    }

    public void setStatusTarefa(EnumTarefaStatus statusTarefa) {
        this.statusTarefa = statusTarefa;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }
}
