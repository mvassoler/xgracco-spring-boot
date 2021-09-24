package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.LogAuditoria;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.util.Date;

/**
 * Filtro da LogAuditoria
 *
 * Created by Jordano on 08/12/2017
 *
 *
 */
public class LogAuditoriaFilter implements Filter<LogAuditoria> {

   private Date dataInicio;

   private Date dataFim;

    public LogAuditoriaFilter(Date dataInicio, Date dataFim) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }
}
