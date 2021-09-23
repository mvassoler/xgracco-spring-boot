package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

public enum EnumTipoEnderecoEletronico implements PersistentEnum {

    ENDERECO_HOME_PAGE(0, "Home Page"),
    ENDERECO_EMAIL(1, "E-mail"),
    ENDERECO_TWITTER(2, "Twitter"),
    ENDERECO_FACEBOOK(3, "Facebook");

    private final int id;
    private final String descricao;

    EnumTipoEnderecoEletronico(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumTipoEnderecoEletronico getById(int id) {
        return Stream.of(EnumTipoEnderecoEletronico.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoEnderecoEletronico.class, String.valueOf(id)));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
