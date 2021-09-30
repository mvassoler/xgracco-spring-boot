package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumEstadoCivil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by felipiberdun on 18/01/2017.
 */
@Converter(autoApply = true)
public class EnumEstadoCivilConverter implements AttributeConverter<EnumEstadoCivil, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumEstadoCivil estadoCivil) {
        return estadoCivil == null ? null : estadoCivil.getId();
    }

    @Override
    public EnumEstadoCivil convertToEntityAttribute(Integer estadoCivil) {
        return estadoCivil == null ? null : EnumEstadoCivil.getById(estadoCivil);
    }

}
