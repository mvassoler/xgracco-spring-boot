package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

public enum EnumProcessoCertificacaoStatus implements PersistentEnum {

    CERTIFICADO(1, "Certificado em @"),
    AGUARDANDO_TRATAMENTO(2, "Aguardando tratamento"),
    AGUARDANDO_CERTIFICACAO(3, "Aguardando certificação"),
    ENVIO_PENDENTE(4, "Envio pendente"),;

    private final int id;
    private final String descricao;

    EnumProcessoCertificacaoStatus(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumProcessoCertificacaoStatus getById(int id) {
        return Stream.of(EnumProcessoCertificacaoStatus.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumProcessoCertificacaoStatus.class, String.valueOf(id)));
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
