package br.com.finchsolucoes.xgracco.domain.eventlistener.events;

import br.com.finchsolucoes.xgracco.domain.enums.EnumAcessoLogAcaoOrigem;

public class AcessoUsuarioLogEvent {

    private final String ipAddress;
    private final String idSessao;
    private final String username;
    private final EnumAcessoLogAcaoOrigem enumAcessoLogAcaoOrigem;
    private final String token;

    public AcessoUsuarioLogEvent(String ipAddress, String idSessao, String username, EnumAcessoLogAcaoOrigem enumAcessoLogAcaoOrigem, String token) {

        this.ipAddress = ipAddress;
        this.idSessao = idSessao;
        this.username = username;
        this.enumAcessoLogAcaoOrigem = enumAcessoLogAcaoOrigem;
        this.token = token;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getIdSessao() {
        return idSessao;
    }

    public String getUsername() {
        return username;
    }

    public EnumAcessoLogAcaoOrigem getEnumAcessoLogAcaoOrigem() {
        return enumAcessoLogAcaoOrigem;
    }

    public String getToken() {
        return token;
    }
}
