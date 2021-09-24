package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Formulario;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de formulário.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class FormularioFilter implements Filter<Formulario> {

    private final String nome;

    public FormularioFilter(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
