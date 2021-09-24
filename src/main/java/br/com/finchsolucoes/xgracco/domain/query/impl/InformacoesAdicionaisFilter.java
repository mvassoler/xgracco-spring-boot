package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de Informacoes Adicionais.
 *
 * @author Paulo Marcon
 * @since 2.1
 */
public class InformacoesAdicionaisFilter implements Filter<InformacoesAdicionais> {

    private String conteudo;
    private Processo processo;
    private Pessoa pessoa;
    private ProcessoGarantia processoGarantia;
    private Campo campo;
    private Boolean campoAtivo;

    public InformacoesAdicionaisFilter(Campo campo) {
        this.campo = campo;
    }

    public InformacoesAdicionaisFilter(Campo campo, String conteudo) {
        this.campo = campo;
        this.conteudo = conteudo;
    }

    public InformacoesAdicionaisFilter(String conteudo, Processo processo, Pessoa pessoa, ProcessoGarantia processoGarantia, Campo campo, Boolean campoAtivo) {
        this.conteudo = conteudo;
        this.processo = processo;
        this.pessoa = pessoa;
        this.processoGarantia = processoGarantia;
        this.campo = campo;
        this.campoAtivo = campoAtivo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public Processo getProcesso() {
        return processo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public ProcessoGarantia getProcessoGarantia() {
        return processoGarantia;
    }

    public Campo getCampo() {
        return campo;
    }

    public Boolean getCampoAtivo() {
        return campoAtivo;
    }
}
