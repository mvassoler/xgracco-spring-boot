package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Rotina;
import br.com.finchsolucoes.xgracco.domain.entity.RotinaExecucao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumRotinaJob;
import br.com.finchsolucoes.xgracco.domain.enums.EnumRotinaStatus;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.util.Calendar;

/**
 * Repositório da entidade RotinaExecucao.
 *
 * @author Renan Gigliotti
 * @since 2.0
 */
public class RotinaExecucaoFilter implements Filter<RotinaExecucao> {

    private Rotina rotina;
    private Long id;
    private String descricao;
    private EnumRotinaJob job;
    private EnumRotinaStatus status;
    private Calendar dataExecucao;
    private Boolean ciencia;

    public RotinaExecucaoFilter(EnumRotinaJob job, EnumRotinaStatus status) {
        super();
        this.job = job;
        this.status = status;
    }

    public RotinaExecucaoFilter(Rotina rotina, Long id, String descricao, EnumRotinaJob job, EnumRotinaStatus status, Calendar dataExecucao, Boolean ciencia) {
        this(job, status);
        this.rotina = rotina;
        this.id = id;
        this.descricao = descricao;
        this.status = status;
        this.dataExecucao = dataExecucao;
        this.ciencia = ciencia;
    }

    public Rotina getRotina() {
        return rotina;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public EnumRotinaJob getJob() {
        return job;
    }

    public EnumRotinaStatus getStatus() {
        return status;
    }

    public Calendar getDataExecucao() {
        return dataExecucao;
    }

    public Boolean getCiencia() {
        return ciencia;
    }
}

