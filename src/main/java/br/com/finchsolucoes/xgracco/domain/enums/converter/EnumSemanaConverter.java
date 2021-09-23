package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumSemana;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by renan on 13/12/16.
 */
@Converter(autoApply = true)
public class EnumSemanaConverter implements AttributeConverter<EnumSemana, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumSemana semana) {
        return semana == null ? null : semana.getId();
    }

    @Override
    public EnumSemana convertToEntityAttribute(Integer semana) {
        return semana == null ? null : EnumSemana.getById(semana);
    }
}
