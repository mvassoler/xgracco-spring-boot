package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by felipiberdun on 24/10/2016.
 */
@Converter(autoApply = true)
public class EnumTipoJusticaConverter implements AttributeConverter<EnumTipoJustica, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoJustica tipoJustica) {
        return tipoJustica == null ? null : tipoJustica.getId();
    }

    @Override
    public EnumTipoJustica convertToEntityAttribute(Integer tipoJustica) {
        return tipoJustica == null ? null : EnumTipoJustica.getById(tipoJustica);
    }

}
