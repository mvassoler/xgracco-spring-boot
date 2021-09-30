package br.com.finchsolucoes.xgracco.legacy.beans.parametros;

import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.CampoParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;

/**
 * @author Marcelo Aguiar
 */
public class ParametrosEmail implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    @CampoParametro(campo = EnumParametro.EMAIL_SERVIDOR)
    private String servidorMail;

    @CampoParametro(campo = EnumParametro.EMAIL_AUTENTICACAO)
    private boolean autenticacaoMail;

    @CampoParametro(campo = EnumParametro.EMAIL_USUARIO)
    private String usuarioMail;

    @CampoParametro(campo = EnumParametro.EMAIL_SENHA)
    private String senhaMail;

    @CampoParametro(campo = EnumParametro.EMAIL_PORTA)
    private Long portaMail;

    @CampoParametro(campo = EnumParametro.EMAIL_PROXY)
    private boolean proxyMail;

    @CampoParametro(campo = EnumParametro.EMAIL_ENDERECO_PROXY)
    private String enderecoProxyMail;

    @CampoParametro(campo = EnumParametro.EMAIL_PORTA_PROXY)
    private int portaProxyMail;

    @CampoParametro(campo = EnumParametro.EMAIL_REMETENTE)
    private String emailRemetenteMail;

    @CampoParametro(campo = EnumParametro.EMAIL_TLS)
    private boolean tlsMail;

    @CampoParametro(campo = EnumParametro.EMAIL_SSL)
    private boolean sslMail;

    private String emailTeste;

    public String getServidorMail() {
        return servidorMail;
    }

    public void setServidorMail(String servidorMail) {
        this.servidorMail = servidorMail;
    }

    public boolean isAutenticacaoMail() {
        return autenticacaoMail;
    }

    public void setAutenticacaoMail(boolean autenticacaoMail) {
        this.autenticacaoMail = autenticacaoMail;
    }

    public String getUsuarioMail() {
        return usuarioMail;
    }

    public void setUsuarioMail(String usuarioMail) {
        this.usuarioMail = usuarioMail;
    }

    public String getSenhaMail() {
        return senhaMail;
    }

    public void setSenhaMail(String senhaMail) {
        this.senhaMail = senhaMail;
    }

    public Long getPortaMail() {
        return portaMail;
    }

    public void setPortaMail(Long portaMail) {
        this.portaMail = portaMail;
    }

    public boolean isProxyMail() {
        return proxyMail;
    }

    public void setProxyMail(boolean proxyMail) {
        this.proxyMail = proxyMail;
    }

    public String getEnderecoProxyMail() {
        return enderecoProxyMail;
    }

    public void setEnderecoProxyMail(String enderecoProxyMail) {
        this.enderecoProxyMail = enderecoProxyMail;
    }

    public int getPortaProxyMail() {
        return portaProxyMail;
    }

    public void setPortaProxyMail(int portaProxyMail) {
        this.portaProxyMail = portaProxyMail;
    }

    public String getEmailRemetenteMail() {
        return emailRemetenteMail;
    }

    public void setEmailRemetenteMail(String emailRemetenteMail) {
        this.emailRemetenteMail = emailRemetenteMail;
    }

    public boolean isTlsMail() {
        return tlsMail;
    }

    public void setTlsMail(boolean tlsMail) {
        this.tlsMail = tlsMail;
    }

    public boolean isSslMail() {
        return sslMail;
    }

    public void setSslMail(boolean sslMail) {
        this.sslMail = sslMail;
    }

    public String getEmailTeste() {
        return emailTeste;
    }

    public void setEmailTeste(String emailTeste) {
        this.emailTeste = emailTeste;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPK() {
        return id;
    }

    public String getTextoLog() {
        return "Servidor e-mail: " + servidorMail + " | Remetente: " + emailRemetenteMail;
    }
}
