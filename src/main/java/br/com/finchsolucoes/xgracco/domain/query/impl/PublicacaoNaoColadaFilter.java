package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.PublicacaoNaoColada;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusPublicacaoNaoColada;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class PublicacaoNaoColadaFilter implements Filter<PublicacaoNaoColada> {

    private String partes;
    private EnumStatusPublicacaoNaoColada status;
    private String numeroProcesso;

    public PublicacaoNaoColadaFilter(String partes, EnumStatusPublicacaoNaoColada status, String numeroProcesso) {
        this.partes = partes;
        this.status = status;
        this.numeroProcesso = numeroProcesso;
    }

    public String getPartes() {
        return partes;
    }

    public EnumStatusPublicacaoNaoColada getStatus() {
        return status;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }
}