package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoParte;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de Parte.
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
public class ParteFilter implements Filter<ProcessoParte> {

    private Long idTipoParte;
    private Pessoa pessoa;

    public ParteFilter(Long idTipoParte) {
        this.idTipoParte = idTipoParte;
    }

    public ParteFilter(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Long getIdTipoParte() {
        return idTipoParte;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
}
