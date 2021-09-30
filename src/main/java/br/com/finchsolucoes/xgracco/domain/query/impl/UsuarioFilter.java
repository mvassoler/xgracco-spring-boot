package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.enums.EnumFuncao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPessoa;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

/**
 * Filtro de usuário.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class UsuarioFilter implements Filter<Usuario> {

    private Set<String> login;
    private Set<String> nome;
    private Set<String> email;
    private Set<EnumFuncao> funcoes;
    private Set<EnumTipoPessoa> tipoPessoa;
    private Set<Boolean> bloqueado;
    private Set<String> fullText;
    private Boolean ImportacaoPlanilha = false;
    private Boolean usuarioLogado = false;

    @JsonProperty("perfil.id")
    private Set<Long> perfilId;
    @JsonProperty("perfil.nome")
    private Set<String> perfilNome;
    @JsonProperty("perfil.descricao")
    private Set<String> perfilDescricao;

    @JsonProperty("escritorio")
    private Set<Boolean> escritorio;
    @JsonProperty("escritorio.id")
    private Set<Long> escritorioId;
    @JsonProperty("escritorio.nomeRazaoSocial")
    private Set<String> escritorioNomeRazaoSocial;
    private Set<Boolean> exibirCoordenadorDepartamento;

    @JsonProperty("carteira")
    private Set<Boolean> carteira;
    @JsonProperty("carteira.id")
    private Set<Long> carteiraId;
    @JsonProperty("carteira.uid")
    private Set<String> carteiraUid;
    @JsonProperty("carteira.descricao")
    private Set<String> carteiraDescricao;
    @JsonProperty("carteira.repetidos")
    private Set<Long> carteiraRepetidos;

    public UsuarioFilter() {
    }

    public Set<String> getLogin() {
        return login;
    }

    public void setLogin(Set<String> login) {
        this.login = login;
    }

    public Set<String> getNome() {
        return nome;
    }

    public void setNome(Set<String> nome) {
        this.nome = nome;
    }

    public Set<String> getEmail() {
        return email;
    }

    public void setEmail(Set<String> email) {
        this.email = email;
    }

    public Set<EnumFuncao> getFuncoes() {
        return funcoes;
    }

    public void setFuncoes(Set<EnumFuncao> funcoes) {
        this.funcoes = funcoes;
    }

    public Set<EnumTipoPessoa> getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(Set<EnumTipoPessoa> tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public Set<Boolean> getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Set<Boolean> bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Set<String> getFullText() {
        return fullText;
    }

    public void setFullText(Set<String> fullText) {
        this.fullText = fullText;
    }

    public Set<Long> getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Set<Long> perfilId) {
        this.perfilId = perfilId;
    }

    public Set<String> getPerfilNome() {
        return perfilNome;
    }

    public void setPerfilNome(Set<String> perfilNome) {
        this.perfilNome = perfilNome;
    }

    public Set<String> getPerfilDescricao() {
        return perfilDescricao;
    }

    public void setPerfilDescricao(Set<String> perfilDescricao) {
        this.perfilDescricao = perfilDescricao;
    }

    public Set<Boolean> getEscritorio() {
        return escritorio;
    }

    public void setEscritorio(Set<Boolean> escritorio) {
        this.escritorio = escritorio;
    }

    public Set<Long> getEscritorioId() {
        return escritorioId;
    }

    public void setEscritorioId(Set<Long> escritorioId) {
        this.escritorioId = escritorioId;
    }

    public Set<String> getEscritorioNomeRazaoSocial() {
        return escritorioNomeRazaoSocial;
    }

    public void setEscritorioNomeRazaoSocial(Set<String> escritorioNomeRazaoSocial) {
        this.escritorioNomeRazaoSocial = escritorioNomeRazaoSocial;
    }

    public Set<Boolean> getCarteira() {
        return carteira;
    }

    public void setCarteira(Set<Boolean> carteira) {
        this.carteira = carteira;
    }

    public Set<Long> getCarteiraId() {
        return carteiraId;
    }

    public void setCarteiraId(Set<Long> carteiraId) {
        this.carteiraId = carteiraId;
    }

    public Set<String> getCarteiraUid() {
        return carteiraUid;
    }

    public void setCarteiraUid(Set<String> carteiraUid) {
        this.carteiraUid = carteiraUid;
    }

    public Set<String> getCarteiraDescricao() {
        return carteiraDescricao;
    }

    public void setCarteiraDescricao(Set<String> carteiraDescricao) {
        this.carteiraDescricao = carteiraDescricao;
    }

    public Set<Long> getCarteiraRepetidos() {
        return carteiraRepetidos;
    }

    public void setCarteiraRepetidos(Set<Long> carteiraRepetidos) {
        this.carteiraRepetidos = carteiraRepetidos;
    }

    public Set<Boolean> isExibirCoordenadorDepartamento() {
        return exibirCoordenadorDepartamento;
    }

    public void setExibirCoordenadorDepartamento(Set<Boolean> exibirCoordenadorDepartamento) {
        this.exibirCoordenadorDepartamento = exibirCoordenadorDepartamento;
    }

    public Boolean getImportacaoPlanilha() {
        return ImportacaoPlanilha;
    }

    public void setImportacaoPlanilha(Boolean importacaoPlanilha) {
        ImportacaoPlanilha = importacaoPlanilha;
    }

    public Boolean getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Boolean usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}
