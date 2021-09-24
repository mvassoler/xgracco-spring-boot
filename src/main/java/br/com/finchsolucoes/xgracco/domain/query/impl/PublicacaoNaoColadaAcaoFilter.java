package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.PublicacaoNaoColadaAcao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumPublicacaoNaoColadaAcao;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.time.LocalDateTime;

public class PublicacaoNaoColadaAcaoFilter implements Filter<PublicacaoNaoColadaAcao> {

    private EnumPublicacaoNaoColadaAcao acao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    public PublicacaoNaoColadaAcaoFilter(EnumPublicacaoNaoColadaAcao acao, LocalDateTime dataInicio, LocalDateTime dataFim) {
        this.acao = acao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public EnumPublicacaoNaoColadaAcao getAcao() {
        return acao;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }
}
