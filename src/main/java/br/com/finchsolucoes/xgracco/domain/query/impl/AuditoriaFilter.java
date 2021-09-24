package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import br.com.finchsolucoes.xgracco.legacy.beans.dto.Auditoria;

import java.util.Calendar;

/**
 * Filtro de auditoria.
 *
 * @author Jordano
 * @since 2.1
 */
public class AuditoriaFilter implements Filter<Auditoria> {

    private Usuario usuario;

    private Calendar dataInicio;

    private Calendar dataFim;

    private Class classe;

    public AuditoriaFilter(Usuario usuario, Calendar dataInicio, Calendar dataFim, Class classe) {
        this.usuario = usuario;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.classe = classe;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Calendar getDataInicio() {
        return dataInicio;
    }

    public Calendar getDataFim() {
        return dataFim;
    }

    public Class getClasse() {
        return classe;
    }
}
