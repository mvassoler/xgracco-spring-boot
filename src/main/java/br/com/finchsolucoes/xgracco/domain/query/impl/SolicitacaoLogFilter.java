package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoLog;
import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusSolicitacao;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * @author Thiago Fogar
 * @since 2.2.4
 */
public class SolicitacaoLogFilter implements Filter<SolicitacaoLog> {

    private final Long id;

    private final Pessoa solicitante;

    private final Usuario usuarioLogado;

    private Long idProcesso;

    private EnumStatusSolicitacao statusSolicitacao;

    private boolean incluirConcluidas;

    public SolicitacaoLogFilter(Long id, Pessoa solicitante, Usuario usuarioLogado) {
        this.id = id;
        this.solicitante = solicitante;
        this.usuarioLogado = usuarioLogado;
    }

    public SolicitacaoLogFilter(Long id, Pessoa solicitante, Usuario usuarioLogado, Long idProcesso, boolean incluirConcluidas) {
        this.id = id;
        this.solicitante = solicitante;
        this.usuarioLogado = usuarioLogado;
        this.idProcesso = idProcesso;
        this.incluirConcluidas = incluirConcluidas;
    }

    public SolicitacaoLogFilter(Long id, Pessoa solicitante, Usuario usuarioLogado, Long idProcesso, EnumStatusSolicitacao statusSolicitacao, boolean incluirConcluidas) {
        this.id = id;
        this.solicitante = solicitante;
        this.usuarioLogado = usuarioLogado;
        this.idProcesso = idProcesso;
        this.statusSolicitacao = statusSolicitacao;
        this.incluirConcluidas = incluirConcluidas;
    }

    public Long getIdProcesso() {
        return idProcesso;
    }

    public Long getId() {
        return id;
    }

    public Pessoa getSolicitante() {
        return solicitante;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public EnumStatusSolicitacao getStatusSolicitacao() {
        return statusSolicitacao;
    }

    public boolean isIncluirConcluidas() {
        return incluirConcluidas;
    }
}
