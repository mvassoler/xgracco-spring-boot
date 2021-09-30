package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by felipiberdun on 29/12/2016.
 */
@Relation(collectionRelation = "instancias")
public enum EnumInstancia implements Serializable {

    PRIMEIRA(1, "1° Instância"),
    SEGUNDA(2, "2° Instância"),
    TERCEIRA(3, "3° Instância"),
    SUPERIOR(4, "Superior"),
    SUPREMO(5, "Supremo");

    private final int id;
    private final String descricao;

    EnumInstancia(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EnumInstancia getById(int id) {
        return Stream.of(EnumInstancia.values())
                .filter(instancia -> instancia.id == id).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumInstancia.class, String.valueOf(id)));
    }

    public static EnumInstancia getByDescricao(final String descricao) {
        if (descricao == null) {
            return null;
        }
        return Stream.of(EnumInstancia.values())
                .filter(enumInstancia -> Util.like(enumInstancia.getDescricao(), descricao)).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumInstancia.class, descricao));
    }

    @Override
    public String toString() {
        return descricao;
    }
}
