package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumProcessoTipoPerda;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumProcessoTipoPerdaConverter implements AttributeConverter<EnumProcessoTipoPerda, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumProcessoTipoPerda processoTipoPerda) {
        return processoTipoPerda == null ? null : processoTipoPerda.getId();
    }

    @Override
    public EnumProcessoTipoPerda convertToEntityAttribute(Integer processoTipoPerda) {
        return processoTipoPerda == null ? null : EnumProcessoTipoPerda.getById(processoTipoPerda);
    }
}
