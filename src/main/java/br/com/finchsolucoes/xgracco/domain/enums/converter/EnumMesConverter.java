package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumMes;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by renan on 13/12/16.
 */
@Converter(autoApply = true)
public class EnumMesConverter implements AttributeConverter<EnumMes, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumMes mes) {
        return mes == null ? null : mes.getId();
    }

    @Override
    public EnumMes convertToEntityAttribute(Integer mes) {
        return mes == null ? null : EnumMes.getById(mes);
    }
}
