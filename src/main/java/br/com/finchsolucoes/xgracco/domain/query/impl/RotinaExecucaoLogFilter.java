package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Rotina;
import br.com.finchsolucoes.xgracco.domain.entity.RotinaExecucao;
import br.com.finchsolucoes.xgracco.domain.entity.RotinaExecucaoLog;
import br.com.finchsolucoes.xgracco.domain.enums.EnumRotinaStatus;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.util.Calendar;
import java.util.List;

/**
 * Repositório da entidade RotinaExecucaoLog.
 *
 * @author Renan Gigliotti
 * @since 2.0
 */
public class RotinaExecucaoLogFilter implements Filter<RotinaExecucaoLog> {

    private final Rotina rotina;
    private final RotinaExecucao execucao;
    private final Long id;
    private final String descricao;
    private final List<EnumRotinaStatus> status;
    private final Calendar dataExecucao;

    public RotinaExecucaoLogFilter(Rotina rotina, RotinaExecucao execucao, Long id, String descricao, List<EnumRotinaStatus> status, Calendar dataExecucao) {
        this.rotina = rotina;
        this.execucao = execucao;
        this.id = id;
        this.descricao = descricao;
        this.status = status;
        this.dataExecucao = dataExecucao;
    }

    public Rotina getRotina() {
        return rotina;
    }

    public RotinaExecucao getExecucao() {
        return execucao;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<EnumRotinaStatus> getStatus() {
        return status;
    }

    public Calendar getDataExecucao() {
        return dataExecucao;
    }
}

