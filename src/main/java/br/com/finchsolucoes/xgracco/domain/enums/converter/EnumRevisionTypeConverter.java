package br.com.finchsolucoes.xgracco.domain.enums.converter;

import org.hibernate.envers.RevisionType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by paulomarcon
 */
@Converter(autoApply = true)
public class EnumRevisionTypeConverter implements AttributeConverter<RevisionType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RevisionType revisionType) {
        return revisionType == null ? null : revisionType.getRepresentation().intValue();
    }

    @Override
    public RevisionType convertToEntityAttribute(Integer valor) {
        return valor == null ? null : RevisionType.fromRepresentation(valor.byteValue());
    }
}
