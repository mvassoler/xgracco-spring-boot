package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.CalculoPrecificacao;
import br.com.finchsolucoes.xgracco.domain.entity.CalculoPrecificacaoExecucaoLog;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class CalculoPrecificacaoLogsFilter  implements Filter<CalculoPrecificacaoExecucaoLog>{

    private CalculoPrecificacao calculoPrecificacao;
    private Boolean processoAtualizado;
    private String processoUnico;

    public CalculoPrecificacaoLogsFilter(CalculoPrecificacao calculoPrecificacao, Boolean processoAtualizado, String processoUnico){
        this.calculoPrecificacao = calculoPrecificacao;
        this.processoAtualizado = processoAtualizado;
        this.processoUnico = processoUnico;
    }

    public CalculoPrecificacao getCalculoPrecificacao() {
        return calculoPrecificacao;
    }

    public Boolean getProcessoAtualizado() {
        return processoAtualizado;
    }

    public String getProcessoUnico() {
        return processoUnico;
    }
}
