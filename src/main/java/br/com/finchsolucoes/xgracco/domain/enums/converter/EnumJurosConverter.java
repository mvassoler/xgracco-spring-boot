package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumJuros;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumJurosConverter implements AttributeConverter<EnumJuros, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumJuros juros) {
        return juros == null ? null : juros.getId();
    }

    @Override
    public EnumJuros convertToEntityAttribute(Integer juros) {
        return juros == null ? null : EnumJuros.getById(juros);
    }
}
