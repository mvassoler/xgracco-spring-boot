package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoIndiceEconomico;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by felipiberdun on 11/01/2017.
 */
@Converter(autoApply = true)
public class EnumTipoIndiceEconomicoConverter implements AttributeConverter<EnumTipoIndiceEconomico, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoIndiceEconomico tipoIndiceEconomico) {
        return tipoIndiceEconomico == null ? null : tipoIndiceEconomico.getId();
    }

    @Override
    public EnumTipoIndiceEconomico convertToEntityAttribute(Integer tipoIndiceEconomico) {
        return tipoIndiceEconomico == null ? null : EnumTipoIndiceEconomico.getById(tipoIndiceEconomico);
    }

}
