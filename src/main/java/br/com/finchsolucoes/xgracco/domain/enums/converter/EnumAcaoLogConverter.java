package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumAcaoLog;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by jordano on 07/10/16.
 */
@Converter(autoApply = true)
public class EnumAcaoLogConverter implements AttributeConverter<EnumAcaoLog, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumAcaoLog enumAcaoLog) {
        return enumAcaoLog == null ? null : enumAcaoLog.getId();
    }

    @Override
    public EnumAcaoLog convertToEntityAttribute(Integer enumAcaoLog) {
        return enumAcaoLog == null ? null : EnumAcaoLog.getById(enumAcaoLog);
    }
}
