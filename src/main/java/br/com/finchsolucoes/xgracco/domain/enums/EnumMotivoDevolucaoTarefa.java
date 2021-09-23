package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

public enum EnumMotivoDevolucaoTarefa implements PersistentEnum {

    FALTA_DOCUMENTACAO(0, "Falta Documentação"),
    TRANSFERIR(1, "Transferência de Responsável"),
    OUTRO_MOTIVO(2, "Outro Motivo");

    private final int id;
    private final String descricao;

    EnumMotivoDevolucaoTarefa(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumMotivoDevolucaoTarefa getById(int id) {
        return Stream.of(EnumMotivoDevolucaoTarefa.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumMotivoDevolucaoTarefa.class, String.valueOf(id)));
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
