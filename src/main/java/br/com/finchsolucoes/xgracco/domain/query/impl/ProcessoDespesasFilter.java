package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDespesas;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.util.Calendar;
import java.util.List;

/**
 * Filtro de ProcessoDespesas.
 *
 * @author Raphael Moreira
 * @since 4.0.4
 */
public class ProcessoDespesasFilter implements Filter<ProcessoDespesas> {

    private Calendar dataInicio;
    private Calendar dataFim;
    private Pessoa clienteProcesso;
    private List<ProcessoDespesas> processoDespesasList;
    private Pessoa responsavel;

    public ProcessoDespesasFilter(Pessoa responsavel) {
        this.responsavel = responsavel;
    }

    public ProcessoDespesasFilter(Calendar dataInicio, Calendar dataFim, Pessoa clienteProcesso, List<ProcessoDespesas> processoDespesasList) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.clienteProcesso = clienteProcesso;
        this.processoDespesasList = processoDespesasList;
    }

    public Pessoa getClienteProcesso() {
        return clienteProcesso;
    }

    public Calendar getDataFim() {
        return dataFim;
    }

    public Calendar getDataInicio() {
        return dataInicio;
    }

    public List<ProcessoDespesas> getProcessoDespesasList() {
        return processoDespesasList;
    }

    public Pessoa getResponsavel() {
        return responsavel;
    }
}
