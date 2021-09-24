package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Agenda;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.util.Calendar;

/**
 * @author Thiago Fogar
 * @since 2.2.4
 */
public class AgendaFilter implements Filter<Agenda> {

    private final Processo processo;
    private final Long idResponsavel;
    private final Calendar dataAgendamentoInicial;
    private final Calendar dataAgendamentoFinal;
    private final String comecandoCom;

    public AgendaFilter(Processo processo, Long idResponsavel, Calendar dataAgendamentoInicial, Calendar dataAgendamentoFinal, String comecandoCom) {
        this.processo = processo;
        this.idResponsavel = idResponsavel;
        this.dataAgendamentoInicial = dataAgendamentoInicial;
        this.dataAgendamentoFinal = dataAgendamentoFinal;
        this.comecandoCom = comecandoCom;
    }

    public String getComecandoCom() {
        return comecandoCom;
    }

    public Processo getProcesso() {
        return processo;
    }

    public Long getIdResponsavel() {
        return idResponsavel;
    }

    public Calendar getDataAgendamentoInicial() {
        return dataAgendamentoInicial;
    }

    public Calendar getDataAgendamentoFinal() {
        return dataAgendamentoFinal;
    }
}
