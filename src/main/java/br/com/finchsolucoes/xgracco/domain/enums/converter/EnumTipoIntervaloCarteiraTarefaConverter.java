package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoIntervaloCarteiraTarefa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoIntervaloCarteiraTarefaConverter implements AttributeConverter<EnumTipoIntervaloCarteiraTarefa, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoIntervaloCarteiraTarefa tipoIntervaloCarteiraTarefa) {
        return tipoIntervaloCarteiraTarefa == null ? null : tipoIntervaloCarteiraTarefa.getId();
    }

    @Override
    public EnumTipoIntervaloCarteiraTarefa convertToEntityAttribute(Integer tipoIntervaloCarteiraTarefa) {
        return tipoIntervaloCarteiraTarefa == null ? null : EnumTipoIntervaloCarteiraTarefa.getById(tipoIntervaloCarteiraTarefa);
    }
}
