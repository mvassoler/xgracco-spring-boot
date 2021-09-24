package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.AtendimentoFila;
import br.com.finchsolucoes.xgracco.domain.entity.Fila;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.util.Calendar;
import java.util.List;

public class AtendimentoFilaFilter implements Filter<AtendimentoFila> {

    private List<String> tags;
    private Fila fila;
    private String processoUnico;
    private Integer statusTarefa;
    private Calendar dataPrazoInicio;
    private Calendar dataPrazoFim;
    private String tarefa;
    private String memo;
    private String descricaoMotivo;
    private Integer motivoDevolucao;
    private Boolean retornarTarefasEmAtendimento;

    public AtendimentoFilaFilter(List<String> tags,
                                 Fila fila,
                                 String processoUnico,
                                 Integer statusTarefa,
                                 Calendar dataPrazoInicio,
                                 Calendar dataPrazoFim,
                                 String tarefa,
                                 String memo,
                                 String descricaoMotivo,
                                 Integer motivoDevolucao,
                                 Boolean retornarTarefasEmAtendimento) {
        this.tags = tags;
        this.fila = fila;
        this.processoUnico = processoUnico;
        this.statusTarefa = statusTarefa;
        this.dataPrazoInicio = dataPrazoInicio;
        this.dataPrazoFim = dataPrazoFim;
        this.tarefa = tarefa;
        this.memo = memo;
        this.motivoDevolucao = motivoDevolucao;
        this.descricaoMotivo = descricaoMotivo;
        this.retornarTarefasEmAtendimento = retornarTarefasEmAtendimento;
    }

    public List<String> getTags() {
        return tags;
    }

    public Fila getFila() {
        return fila;
    }

    public String getProcessoUnico() {
        return processoUnico;
    }

    public Integer getStatusTarefa() {
        return statusTarefa;
    }

    public Calendar getDataPrazoInicio() {
        return dataPrazoInicio;
    }

    public String getTarefa() {
        return tarefa;
    }

    public String getMemo() {return memo;}

    public Integer getMotivoDevolucao() {return motivoDevolucao;}

    public String getDescricaoMotivo() {return descricaoMotivo;}

    public Calendar getDataPrazoFim() {
        return dataPrazoFim;
    }

    public Boolean isRetornarTarefasEmAtendimento() {
        return retornarTarefasEmAtendimento;
    }
}
