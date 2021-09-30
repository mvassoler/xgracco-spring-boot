package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTarefaStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EnumTarefaStatusConverter implements AttributeConverter<EnumTarefaStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTarefaStatus tarefaStatus) {
        return tarefaStatus == null ? null : tarefaStatus.getId();
    }

    @Override
    public EnumTarefaStatus convertToEntityAttribute(Integer tarefaStatus) {
        return tarefaStatus == null ? null : EnumTarefaStatus.getById(tarefaStatus);
    }
}