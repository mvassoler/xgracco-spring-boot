package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumProcessoEncerramento;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumProcessoEncerramentoConverter implements AttributeConverter<EnumProcessoEncerramento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumProcessoEncerramento processoEncerramento) {
        return processoEncerramento == null ? null : processoEncerramento.getId();
    }

    @Override
    public EnumProcessoEncerramento convertToEntityAttribute(Integer processoEncerramento) {
        return processoEncerramento == null ? null : EnumProcessoEncerramento.getById(processoEncerramento);
    }
}
