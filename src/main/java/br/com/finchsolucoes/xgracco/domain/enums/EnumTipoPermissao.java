package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Tipo de Permissão.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public enum EnumTipoPermissao implements Serializable {

    MODULO(1),
    MENU(2),
    ABA(3),
    BOTAO(4);

    private final int id;

    EnumTipoPermissao(int id) {
        this.id = id;
    }

    public static EnumTipoPermissao getById(int id) {
        return Stream.of(EnumTipoPermissao.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoPermissao.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
