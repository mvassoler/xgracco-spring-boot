package br.com.finchsolucoes.xgracco.domain.enums;

import java.util.stream.Stream;

public enum EnumSistemaExternoUsuario {

    OKTA(1, "OKTA"),
    AD(2, "AD");

    private Integer id;
    private String descricao;

    EnumSistemaExternoUsuario(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumSistemaExternoUsuario getById(int id) {
        return Stream.of(EnumSistemaExternoUsuario.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumSistemaExternoUsuario.class, String.valueOf(id)));
    }

    public static EnumSistemaExternoUsuario getByDescricao(final String descricao) {
        if (descricao == null){
            return null;
        }
        return Stream.of(EnumSistemaExternoUsuario.values())
                .filter(enumSistemaExternoUsuario -> enumSistemaExternoUsuario.getDescricao().trim().toUpperCase().equals(descricao.trim().toUpperCase())).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumSistemaExternoUsuario.class, descricao));
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
