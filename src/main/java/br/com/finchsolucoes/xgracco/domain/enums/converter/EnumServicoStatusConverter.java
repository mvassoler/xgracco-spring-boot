package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumServicoStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by renan on 13/09/16.
 */

@Converter(autoApply = true)
public class EnumServicoStatusConverter implements AttributeConverter<EnumServicoStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumServicoStatus status) {
        return status == null ? null : status.getId();
    }

    @Override
    public EnumServicoStatus convertToEntityAttribute(Integer status) {
        return status == null ? null : EnumServicoStatus.getById(status);
    }
}