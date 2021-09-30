package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPapel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoPapelConverter implements AttributeConverter<EnumTipoPapel, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoPapel tipoPapel) {
        return tipoPapel == null ? null : tipoPapel.getId();
    }

    @Override
    public EnumTipoPapel convertToEntityAttribute(Integer tipoPapel) {
        return tipoPapel == null ? null : EnumTipoPapel.getById(tipoPapel);
    }
}
