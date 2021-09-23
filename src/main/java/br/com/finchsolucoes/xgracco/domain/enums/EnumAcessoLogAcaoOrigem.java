package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

/**
 * Enum responsável por mapear os tipos de ações possíveis relacionadas ao acesso de um usuário no sistema.
 *
 * @autor Eeloi Correia
 * @since 5.46
 */
public enum EnumAcessoLogAcaoOrigem implements PersistentEnum {

    API(1, "API"),
    WEB(2, "WEB"),
    TEST(3, "TEST");

    private final int id;
    private final String descricao;

    EnumAcessoLogAcaoOrigem(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumAcessoLogAcaoOrigem getById(int id) {
        return Stream.of(EnumAcessoLogAcaoOrigem.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumAcessoLogAcaoOrigem.class, String.valueOf(id)));
    }

    public static EnumAcessoLogAcaoOrigem getByDescricao(String descricao) {
        return Stream.of(EnumAcessoLogAcaoOrigem.values())
                .filter(e -> descricao == e.getDescricao()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumAcessoLogAcaoOrigem.class, descricao));
    }


    @Override
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
