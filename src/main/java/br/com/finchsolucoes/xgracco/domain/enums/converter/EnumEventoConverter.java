package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumEvento;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by renan on 04/10/16.
 */
@Converter(autoApply = true)
public class EnumEventoConverter implements AttributeConverter<EnumEvento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumEvento evento) {
        return evento == null ? null : evento.getId();
    }

    @Override
    public EnumEvento convertToEntityAttribute(Integer evento) {
        return evento == null ? null : EnumEvento.getById(evento);
    }
}
