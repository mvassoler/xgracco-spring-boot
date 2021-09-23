package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by felipiberdun on 26/10/2016.
 */
@Converter(autoApply = true)
public class EnumAreaConverter implements AttributeConverter<EnumArea, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumArea enumArea) {
        return enumArea == null ? null : enumArea.getId();
    }

    @Override
    public EnumArea convertToEntityAttribute(Integer enumArea) {
        return enumArea == null ? null : EnumArea.getById(enumArea);
    }

}
