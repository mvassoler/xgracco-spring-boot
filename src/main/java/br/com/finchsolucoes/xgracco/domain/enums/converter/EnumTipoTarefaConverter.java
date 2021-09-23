package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoTarefa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converter enum tipo tarefa
 *
 * @author paulo.marcon
 * @since 5.0.0
 */
@Converter(autoApply = true)
public class EnumTipoTarefaConverter implements AttributeConverter<EnumTipoTarefa, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoTarefa tipo) {
        return tipo == null ? null : tipo.getId();
    }

    @Override
    public EnumTipoTarefa convertToEntityAttribute(Integer integer) {
        return integer == null ? null : EnumTipoTarefa.getById(integer);
    }
}
