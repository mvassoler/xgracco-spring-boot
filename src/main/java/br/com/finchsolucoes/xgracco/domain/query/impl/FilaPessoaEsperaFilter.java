package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.FilaPessoaEspera;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class FilaPessoaEsperaFilter implements Filter<FilaPessoaEspera> {

    private Long idFilaOrigem;
    private String filaDescricaoOrigem;
    private Long idFilaDestino;
    private String filaDescricaoDestino;
    private Long idPessoa;
    private String pessoaNome;
    private Long idFila;

    public FilaPessoaEsperaFilter(Long idFilaOrigem,
                                  String filaDescricaoOrigem,
                                  Long idFilaDestino,
                                  String filaDescricaoDestino,
                                  Long idPessoa,
                                  String pessoaNome,
                                  Long idFila) {
        this.idFilaOrigem = idFilaOrigem;
        this.filaDescricaoOrigem = filaDescricaoOrigem;
        this.idFilaDestino = idFilaDestino;
        this.filaDescricaoDestino = filaDescricaoDestino;
        this.idPessoa = idPessoa;
        this.pessoaNome = pessoaNome;
        this.idFila = idFila;
    }

    public Long getIdFilaOrigem() {
        return idFilaOrigem;
    }

    public String getFilaDescricaoOrigem() {
        return filaDescricaoOrigem;
    }

    public Long getIdFilaDestino() {
        return idFilaDestino;
    }

    public String getFilaDescricaoDestino() {
        return filaDescricaoDestino;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public String getPessoaNome() {
        return pessoaNome;
    }

    public Long getIdFila() {
        return idFila;
    }
}
