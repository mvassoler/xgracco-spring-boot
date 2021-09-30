package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Comarca;
import br.com.finchsolucoes.xgracco.domain.enums.EnumRegiaoUf;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

/**
 * Created by felipiberdun on 18/01/2017.
 */
public class ComarcaFilter implements Filter<Comarca> {

    private Set<String> descricao;
    private Set<EnumRegiaoUf> regiao;
    private Set<Long> uf;
    private Set<EnumTipoJustica> tipoJustica;

    @JsonProperty("pessoa")
    private Set<Boolean> pessoa;
    @JsonProperty("pessoa.id")
    private Set<Long> pessoaId;
    @JsonProperty("pessoa.nomeRazaoSocial")
    private Set<String> pessoaNomeRazaoSocial;

    public ComarcaFilter() {
    }

    public Set<String> getDescricao() {
        return descricao;
    }

    public void setDescricao(Set<String> descricao) {
        this.descricao = descricao;
    }

    public Set<EnumRegiaoUf> getRegiao() {
        return regiao;
    }

    public void setRegiao(Set<EnumRegiaoUf> regiao) {
        this.regiao = regiao;
    }

    public Set<Long> getUf() {
        return uf;
    }

    public void setUf(Set<Long> uf) {
        this.uf = uf;
    }

    public Set<EnumTipoJustica> getTipoJustica() {
        return tipoJustica;
    }

    public void setTipoJustica(Set<EnumTipoJustica> tipoJustica) {
        this.tipoJustica = tipoJustica;
    }

    public Set<Boolean> getPessoa() {
        return pessoa;
    }

    public void setPessoa(Set<Boolean> pessoa) {
        this.pessoa = pessoa;
    }

    public Set<Long> getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Set<Long> pessoaId) {
        this.pessoaId = pessoaId;
    }

    public Set<String> getPessoaNomeRazaoSocial() {
        return pessoaNomeRazaoSocial;
    }

    public void setPessoaNomeRazaoSocial(Set<String> pessoaNomeRazaoSocial) {
        this.pessoaNomeRazaoSocial = pessoaNomeRazaoSocial;
    }
}
