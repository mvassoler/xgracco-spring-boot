package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoArquivo;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * @author Thiago Fogar
 * @since 2.2.5
 */
public class SolicitacaoArquivoFilter implements Filter<SolicitacaoArquivo> {

    private final Long idProcesso;
    private final Long idSolicitacao;
    private final Long idSolicitacaoBoomer;

    public SolicitacaoArquivoFilter(Long idProcesso, Long idSolicitacao, Long idSolicitacaoBoomer) {
        this.idProcesso = idProcesso;
        this.idSolicitacao = idSolicitacao;
        this.idSolicitacaoBoomer = idSolicitacaoBoomer;
    }

    public Long getIdProcesso() {
        return idProcesso;
    }

    public Long getIdSolicitacao() {
        return idSolicitacao;
    }

    public Long getIdSolicitacaoBoomer() {
        return idSolicitacaoBoomer;
    }
}
