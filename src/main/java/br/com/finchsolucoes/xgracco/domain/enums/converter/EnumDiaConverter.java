package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumDia;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by renan on 13/12/16.
 */
@Converter(autoApply = true)
public class EnumDiaConverter implements AttributeConverter<EnumDia, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumDia dia) {
        return dia == null ? null : dia.getId();
    }

    @Override
    public EnumDia convertToEntityAttribute(Integer dia) {
        return dia == null ? null : EnumDia.getById(dia);
    }
}

