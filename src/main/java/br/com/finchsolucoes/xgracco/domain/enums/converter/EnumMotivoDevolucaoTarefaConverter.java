package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumMotivoDevolucaoTarefa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumMotivoDevolucaoTarefaConverter implements AttributeConverter<EnumMotivoDevolucaoTarefa, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumMotivoDevolucaoTarefa motivoDevolucaoTarefa) {
        return motivoDevolucaoTarefa == null ? null : motivoDevolucaoTarefa.getId();
    }

    @Override
    public EnumMotivoDevolucaoTarefa convertToEntityAttribute(Integer motivoDevolucaoTarefa) {
        return motivoDevolucaoTarefa == null ? null : EnumMotivoDevolucaoTarefa.getById(motivoDevolucaoTarefa);
    }
}
