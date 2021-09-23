package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoResponsavelCarteiraTarefa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoResponsavelCarteiraTarefaConverter implements AttributeConverter<EnumTipoResponsavelCarteiraTarefa, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoResponsavelCarteiraTarefa tipoResponsavelCarteiraTarefa) {
        return tipoResponsavelCarteiraTarefa == null ? null : tipoResponsavelCarteiraTarefa.getId();
    }

    @Override
    public EnumTipoResponsavelCarteiraTarefa convertToEntityAttribute(Integer tipoResponsavelCarteiraTarefa) {
        return tipoResponsavelCarteiraTarefa == null ? null : EnumTipoResponsavelCarteiraTarefa.getById(tipoResponsavelCarteiraTarefa);
    }
}
