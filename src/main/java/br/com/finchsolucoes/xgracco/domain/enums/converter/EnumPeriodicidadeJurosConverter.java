package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumPeriodicidadeJuros;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by felipiberdun on 29/11/2016.
 */
@Converter(autoApply = true)
public class EnumPeriodicidadeJurosConverter implements AttributeConverter<EnumPeriodicidadeJuros, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumPeriodicidadeJuros enumPeriodicidadeJuros) {
        return enumPeriodicidadeJuros == null ? null : enumPeriodicidadeJuros.getId();
    }

    @Override
    public EnumPeriodicidadeJuros convertToEntityAttribute(Integer periodicidadeJuros) {
        return periodicidadeJuros == null ? null : EnumPeriodicidadeJuros.getById(periodicidadeJuros);
    }
}
