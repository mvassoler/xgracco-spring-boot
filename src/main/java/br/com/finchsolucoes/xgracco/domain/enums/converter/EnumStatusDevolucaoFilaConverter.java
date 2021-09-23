package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusDevolucaoFila;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumStatusDevolucaoFilaConverter implements AttributeConverter<EnumStatusDevolucaoFila, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumStatusDevolucaoFila statusDevolucaoFila) {
        return statusDevolucaoFila == null ? null : statusDevolucaoFila.getId();
    }

    @Override
    public EnumStatusDevolucaoFila convertToEntityAttribute(Integer statusDevolucaoFila) {
        return statusDevolucaoFila == null ? null : EnumStatusDevolucaoFila.getById(statusDevolucaoFila);
    }
}
