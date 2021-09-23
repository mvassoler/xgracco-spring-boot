package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

/**
 * Enum responsável por mapear os tipos de ações possíveis relacionadas ao acesso de um usuário no sistema.
 *
 * @autor Roberto Amadeu Neto
 * @since 5.0.3
 */
public enum EnumAcessoLogAcaoLogoff implements PersistentEnum {

    LOGOFF(1, "Usuario realizou logoff"),
    TEMPO(2, "Desconectado por tempo"),
    SISTEMA(3, "Desconectado por outro usuário");

    private final int id;
    private final String descricao;

    EnumAcessoLogAcaoLogoff(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumAcessoLogAcaoLogoff getById(int id) {
        return Stream.of(EnumAcessoLogAcaoLogoff.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumAcessoLogAcaoLogoff.class, String.valueOf(id)));
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
