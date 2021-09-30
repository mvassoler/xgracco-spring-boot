package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumFuncao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPapel;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPessoa;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro da entidade Pessoa
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class PessoaFilter implements Filter<Pessoa> {

    private String nomeRazaoSocial;
    private String apelidoFantasia;
    private String cpfCnpj;
    private EnumTipoPessoa tipoPessoa;
    private EnumTipoPapel tipoPapel;
    private EnumFuncao funcao;
    private Boolean ativo;
    private Boolean carteira;
    private Long carteiraId;
    private Boolean usuario;
    private Long usuarioId;
    private String fullText;
    private Boolean escritorio;
    private Long escritorioId;
    private Long ignorarPessoaId;


    public PessoaFilter(Boolean ativo, String nomeRazaoSocial) {
        this.nomeRazaoSocial = nomeRazaoSocial;
        this.ativo = ativo;
    }

    public PessoaFilter(String nomeRazaoSocial, String apelidoFantasia, String cpfCnpj, EnumTipoPessoa tipoPessoa, EnumTipoPapel tipoPapel, EnumFuncao funcao, Boolean ativo, Boolean carteira, Long carteiraId, Boolean usuario, Long usuarioId, String fullText, Boolean escritorio, Long escritorioId, Long ignorarPessoaId) {
        this.nomeRazaoSocial = nomeRazaoSocial;
        this.apelidoFantasia = apelidoFantasia;
        this.cpfCnpj = cpfCnpj;
        this.tipoPessoa = tipoPessoa;
        this.tipoPapel = tipoPapel;
        this.funcao = funcao;
        this.ativo = ativo;
        this.carteira = carteira;
        this.carteiraId = carteiraId;
        this.usuario = usuario;
        this.usuarioId = usuarioId;
        this.fullText = fullText;
        this.escritorio = escritorio;
        this.escritorioId = escritorioId;
        this.ignorarPessoaId = ignorarPessoaId;
    }

    public String getNomeRazaoSocial() {
        return nomeRazaoSocial;
    }

    public String getApelidoFantasia() {
        return apelidoFantasia;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public EnumTipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public EnumTipoPapel getTipoPapel() {
        return tipoPapel;
    }

    public EnumFuncao getFuncao() {
        return funcao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public Boolean getCarteira() {
        return carteira;
    }

    public Long getCarteiraId() {
        return carteiraId;
    }

    public Boolean getUsuario() {
        return usuario;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getFullText() {
        return fullText;
    }

    public Boolean getEscritorio() {
        return escritorio;
    }

    public Long getEscritorioId() {
        return escritorioId;
    }

    public Long getIgnorarPessoaId() {
        return ignorarPessoaId;
    }
}
