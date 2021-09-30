package br.com.finchsolucoes.xgracco.domain.enums;

import java.util.stream.Stream;

public enum EnumStatusOktaApi {

    SUCESSO(1, "SUCCESS"),
    PASSWORD_EXPIRED(2, "PASSWORD_EXPIRED"),
    MFA_ENROLL(3, "MFA_ENROLL"),
    MFA_REQUIRED(4, "MFA_REQUIRED");

    private Integer id;
    private String descricao;

    EnumStatusOktaApi(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumStatusOktaApi getById(int id) {
        return Stream.of(EnumStatusOktaApi.values())
                .filter(e -> id == e.getId()).findAny()
                .orElse(null);
    }

    public static EnumStatusOktaApi getByDescricao(final String descricao) {
        return Stream.of(EnumStatusOktaApi.values())
                .filter(enumStatusOktaApi -> enumStatusOktaApi.getDescricao().equals(descricao)).findAny()
                .orElse(null);
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean isSucesso() {
        return id == SUCESSO.id;
    }

    public Boolean isPasswordExpired() {
        return id == PASSWORD_EXPIRED.id;
    }
    
    public Boolean isMfaEnroll() {
        return id == MFA_ENROLL.id;
    }

    public Boolean isMfaRequired() {
        return id == MFA_REQUIRED.id;
    }    
}
