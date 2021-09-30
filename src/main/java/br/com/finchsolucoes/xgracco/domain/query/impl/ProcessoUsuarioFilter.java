package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoUsuario;
import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de GrupoCampoCarteira
 * <p>
 * Created by Jordano on 23/02/2016.
 */
public class ProcessoUsuarioFilter implements Filter<ProcessoUsuario> {

    private Processo processo;

    private Usuario usuario;

    public ProcessoUsuarioFilter(Processo processo, Usuario usuario) {
        this.processo = processo;
        this.usuario = usuario;
    }

    public Processo getProcesso() {
        return processo;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
