package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Vara;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Created by paulomarcon
 */
public class VaraFilter implements Filter<Vara> {

    private String descricao;
    private EnumTipoJustica tipoJustica;
    private EnumInstancia instancia;

    public VaraFilter(String descricao, EnumTipoJustica tipoJustica, EnumInstancia instancia) {
        this.descricao = descricao;
        this.tipoJustica = tipoJustica;
        this.instancia = instancia;
    }

    public String getDescricao() {
        return descricao;
    }

    public EnumTipoJustica getTipoJustica() {
        return tipoJustica;
    }

    public EnumInstancia getInstancia() {
        return instancia;
    }
}
