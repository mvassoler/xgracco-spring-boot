package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumAcaoLogAtendimentoTarefa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * Converter do EnumAcaoLogAtendimentoTarefaConveter
 *
 * @author paulo.marcon
 * @since 5.4.0
 */
@Converter(autoApply = true)
public class EnumAcaoLogAtendimentoTarefaConverter implements AttributeConverter<EnumAcaoLogAtendimentoTarefa, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumAcaoLogAtendimentoTarefa enumAcaoLogAtendimentoTarefa) {
        return Objects.isNull(enumAcaoLogAtendimentoTarefa) ? null : enumAcaoLogAtendimentoTarefa.getId();
    }

    @Override
    public EnumAcaoLogAtendimentoTarefa convertToEntityAttribute(Integer integer) {
        return Objects.isNull(integer) ? null : EnumAcaoLogAtendimentoTarefa.getById(integer);
    }
}
