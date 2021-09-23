package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

public enum EnumNovasAcoesStatus implements PersistentEnum {

    PENDENTE(0, "Pendente"),
    ACEITO(1, "Aceito"),
    REJEITADO(2, "Rejeitado");

    private final int id;
    private final String descricao;

    EnumNovasAcoesStatus(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumNovasAcoesStatus getById(int id) {
        return Stream.of(EnumNovasAcoesStatus.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumNovasAcoesStatus.class, String.valueOf(id)));
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
