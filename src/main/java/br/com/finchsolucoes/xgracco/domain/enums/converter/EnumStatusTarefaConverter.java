package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumStatusTarefaConverter implements AttributeConverter<EnumStatusTarefa, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumStatusTarefa statusTarefa) {
        return statusTarefa == null ? null : statusTarefa.getId();
    }

    @Override
    public EnumStatusTarefa convertToEntityAttribute(Integer statusTarefa) {
        return statusTarefa == null ? null : EnumStatusTarefa.getById(statusTarefa);
    }
}
