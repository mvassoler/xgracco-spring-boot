package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.entity.UsuarioDashboard;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de usuário Dashboard.
 *
 * @author Felipe Costa
 * @since 5.17.0
 */
public class UsuarioDashboardFilter implements Filter<UsuarioDashboard> {

    private Usuario usuario;

    public UsuarioDashboardFilter(Usuario usuario){
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
