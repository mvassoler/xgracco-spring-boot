package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.entity.UsuarioAcessoLog;
import br.com.finchsolucoes.xgracco.domain.enums.EnumAcessoLogAcaoOrigem;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Set;

public class UsuarioAcessoLogFilter implements Filter<UsuarioAcessoLog> {

    @JsonProperty("log.id")
    private Long id;
    private Usuario usuario;
    private LocalDateTime dataLogin;
    private LocalDateTime dataLogoff;
    private EnumAcessoLogAcaoOrigem acessoLogAcaoOrigem;
    private String token;
    private String ip;

    private Boolean somenteEstacoes = false;
    private Boolean somenteOrigens = false;

    @JsonProperty("usuario.id")
    private Set<Long> usuarioId;

    @JsonProperty("usuario.pessoa.id")
    private Set<Long> pessoaId;

    @JsonProperty("usuario.login")
    private Set<String> login;

    @JsonProperty("usuario.nome")
    private Set<String> nome;

    @JsonProperty("desconectado.id")
    private Set<Long> desconectadoId;

    @JsonProperty("desconectado.login")
    private Set<String> desconectadoLogin;

    @JsonProperty("desconectado.nome")
    private Set<String> desconectadoNome;

    @JsonProperty("estacao")
    private Set<String> estacoes;

    @JsonProperty("origem")
    private Set<EnumAcessoLogAcaoOrigem> origens;


    private Boolean estaLogado;

    public UsuarioAcessoLogFilter() {
    }

    public UsuarioAcessoLogFilter(Usuario usuario, String token, String ip) {
        this.usuario = usuario;
        this.token = token;
        this.ip = ip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataLogin() {
        return dataLogin;
    }

    public void setDataLogin(LocalDateTime dataLogin) {
        this.dataLogin = dataLogin;
    }

    public LocalDateTime getDataLogoff() {
        return dataLogoff;
    }

    public void setDataLogoff(LocalDateTime dataLogoff) {
        this.dataLogoff = dataLogoff;
    }

    public EnumAcessoLogAcaoOrigem getAcessoLogAcaoOrigem() {
        return acessoLogAcaoOrigem;
    }

    public void setAcessoLogAcaoOrigem(EnumAcessoLogAcaoOrigem acessoLogAcaoOrigem) {
        this.acessoLogAcaoOrigem = acessoLogAcaoOrigem;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Set<Long> getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Set<Long> usuarioId) {
        this.usuarioId = usuarioId;
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

    public Set<Long> getDesconectadoId() {
        return desconectadoId;
    }

    public void setDesconectadoId(Set<Long> desconectadoId) {
        this.desconectadoId = desconectadoId;
    }

    public Set<String> getDesconectadoLogin() {
        return desconectadoLogin;
    }

    public void setDesconectadoLogin(Set<String> desconectadoLogin) {
        this.desconectadoLogin = desconectadoLogin;
    }

    public Set<String> getDesconectadoNome() {
        return desconectadoNome;
    }

    public void setDesconectadoNome(Set<String> desconectadoNome) {
        this.desconectadoNome = desconectadoNome;
    }

    public Boolean getEstaLogado() {
        return estaLogado;
    }

    public void setEstaLogado(Boolean estaLogado) {
        this.estaLogado = estaLogado;
    }

    public Set<String> getEstacoes() {
        return estacoes;
    }

    public void setEstacoes(Set<String> estacoes) {
        this.estacoes = estacoes;
    }

    public Boolean getSomenteEstacoes() {
        return somenteEstacoes;
    }

    public void setSomenteEstacoes(Boolean somenteEstacoes) {
        this.somenteEstacoes = somenteEstacoes;
    }

    public Boolean getSomenteOrigens() {
        return somenteOrigens;
    }

    public void setSomenteOrigens(Boolean somenteOrigens) {
        this.somenteOrigens = somenteOrigens;
    }

    public Set<EnumAcessoLogAcaoOrigem> getOrigens() {
        return origens;
    }

    public void setOrigens(Set<EnumAcessoLogAcaoOrigem> origens) {
        this.origens = origens;
    }

    public Set<Long> getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Set<Long> pessoaId) {
        this.pessoaId = pessoaId;
    }

}
