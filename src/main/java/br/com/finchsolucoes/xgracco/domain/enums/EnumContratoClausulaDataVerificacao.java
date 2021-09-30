package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

/**
 * @author Marcelo Aguiar
 */
public enum EnumContratoClausulaDataVerificacao implements PersistentEnum {

    CADASTRO(1),
    RECEBIMENTO(2),
    ENCERRAMENTO(3);

    private final int id;

    EnumContratoClausulaDataVerificacao(int id) {
        this.id = id;
    }

    public static EnumContratoClausulaDataVerificacao getById(int id) {
        return Stream.of(EnumContratoClausulaDataVerificacao.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumContratoClausulaDataVerificacao.class, String.valueOf(id)));
    }

    @Override
    public int getId() {
        return id;
    }
}
