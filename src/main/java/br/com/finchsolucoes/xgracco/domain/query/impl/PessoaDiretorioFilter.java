package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaDiretorio;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * @author guilhermecamargo
 */
public class PessoaDiretorioFilter implements Filter<PessoaDiretorio> {

    private String nomeDiretorio;
    private Pessoa pessoa;
    private PessoaDiretorio diretorioPai;

    public PessoaDiretorioFilter(String nomeDiretorio, Pessoa pessoa, PessoaDiretorio diretorioPai) {
        this.nomeDiretorio = nomeDiretorio;
        this.pessoa = pessoa;
        this.diretorioPai = diretorioPai;
    }

    public String getNomeDiretorio() {
        return nomeDiretorio;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public PessoaDiretorio getDiretorioPai() {
        return diretorioPai;
    }
}
