package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

public enum EnumStatusLoteCustas implements PersistentEnum {

    EM_ABERTO(1, "Em Aberto"),
    FECHADO(2, "Fechado");

    private final int id;
    private final String descricao;

    EnumStatusLoteCustas(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumStatusLoteCustas getById(int id) {
        return Stream.of(EnumStatusLoteCustas.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumStatusLoteCustas.class, String.valueOf(id)));
    }

    public static EnumStatusLoteCustas getByDescricao(final String descricao) {
        return Stream.of(EnumStatusLoteCustas.values())
                .filter(enumStatusLoteCustas -> enumStatusLoteCustas.getDescricao().equals(descricao)).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumStatusLoteCustas.class, descricao));
    }

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
