package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Escritorio;
import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.entity.UsuarioEscritorio;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * @author thiago.fogar
 * @since 4.0.2
 */
public class UsuarioEscritorioFilter implements Filter<UsuarioEscritorio> {

    private Usuario usuario;
    private Escritorio escritorio;

    public UsuarioEscritorioFilter(Usuario usuario, Escritorio escritorio) {
        this.usuario = usuario;
        this.escritorio = escritorio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Escritorio getEscritorio() {
        return escritorio;
    }
}
