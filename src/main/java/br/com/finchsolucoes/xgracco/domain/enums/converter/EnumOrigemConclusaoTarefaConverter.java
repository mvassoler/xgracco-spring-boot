package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumOrigemConclusaoTarefa;

import javax.persistence.AttributeConverter;

/**
 * @author guilhermecamargo
 */
public class EnumOrigemConclusaoTarefaConverter  implements AttributeConverter<EnumOrigemConclusaoTarefa, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumOrigemConclusaoTarefa statusPagamento) {
        return statusPagamento == null ? null : statusPagamento.getId();
    }

    @Override
    public EnumOrigemConclusaoTarefa convertToEntityAttribute(Integer statusPagamento) {
        return statusPagamento == null ? null : EnumOrigemConclusaoTarefa.getById(statusPagamento);
    }
}