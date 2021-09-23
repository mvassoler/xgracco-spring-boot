package br.com.finchsolucoes.xgracco.legacy.beans.parametros;

import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.CampoParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;

public class ParametrosPublicacaoEnforce implements Identificavel<Long> {

    private Long id;

    @CampoParametro(campo = EnumParametro.PUBLICACAO_ENFORCE_URL)
    private String url;

    @CampoParametro(campo = EnumParametro.PUBLICACAO_ENFORCE_USUARIO)
    private String usuario;

    @CampoParametro(campo = EnumParametro.PUBLICACAO_ENFORCE_SENHA)
    private String senha;

    @Override
    public Long getPK() {
        return this.id;
    }

    @Override
    public String getTextoLog() {
        return this.url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
