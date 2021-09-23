package br.com.finchsolucoes.xgracco.domain.enums;

import java.util.stream.Stream;

public enum EnumLdapErros {

    USUARIO_NAO_ENCONTRADO("525", "ad.error.user.not.found"),
    CREDENCIAIS_INVALIDAS("52e", "ad.error.invalid.credentials"),
    LOGON_NAO_PERMITIDO_NESTE_MOMENTO("530", "ad.error.not.permitted.to.logon"),
    LOGON_NAO_PERMITIDO_NESTA_WORKSTATION("531", "ad.error.not.permitted.to.logon.workstation"),
    SENHA_EXPIRADA("532", "ad.error.password.expired"),
    USUARIO_DESABILITADO("533", "ad.error.account.disabled"),
    USUARIO_EXPIRADO("701", "ad.error.account.expired"),
    USUARIO_DEVE_RESETAR_SENHA("773", "ad.error.user.must.reset.password"),
    USUARIO_BLOQUEADO("775", "ad.error.user.account.locked");

    private String codigo;
    private String mensagem;

    EnumLdapErros(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public static EnumLdapErros getByCodigo(final String codigo) {

        if (codigo == null){
            return null;
        }
        return Stream.of(EnumLdapErros.values())
                .filter(enumLdapErros -> enumLdapErros.getCodigo().trim().toUpperCase().equals(codigo.trim().toUpperCase())).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumLdapErros.class, codigo));
    }
}
