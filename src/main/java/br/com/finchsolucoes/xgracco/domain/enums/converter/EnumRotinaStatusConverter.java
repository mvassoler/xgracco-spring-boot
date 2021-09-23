package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumRotinaStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumRotinaStatusConverter implements AttributeConverter<EnumRotinaStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumRotinaStatus rotinaStatus) {
        return rotinaStatus == null ? null : rotinaStatus.getId();
    }

    @Override
    public EnumRotinaStatus convertToEntityAttribute(Integer rotinaStatus) {
        return rotinaStatus == null ? null : EnumRotinaStatus.getById(rotinaStatus);
    }
}
