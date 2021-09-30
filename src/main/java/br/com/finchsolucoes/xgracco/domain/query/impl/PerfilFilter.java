package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Perfil;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de perfil.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class PerfilFilter implements Filter<Perfil> {

    private final String nome;
    private final String descricao;
    private final Boolean usuario;

    public PerfilFilter(String nome, String descricao, Boolean usuario) {
        this.nome = nome;
        this.descricao = descricao;
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getUsuario() {
        return usuario;
    }
}
