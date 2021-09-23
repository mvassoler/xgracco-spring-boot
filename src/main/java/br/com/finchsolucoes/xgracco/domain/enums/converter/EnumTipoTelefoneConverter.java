package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoTelefone;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by felipiberdun on 11/01/2017.
 */
@Converter(autoApply = true)
public class EnumTipoTelefoneConverter implements AttributeConverter<EnumTipoTelefone, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoTelefone tipoTelefone) {
        return tipoTelefone == null ? null : tipoTelefone.getId();
    }

    @Override
    public EnumTipoTelefone convertToEntityAttribute(Integer tipoTelefone) {
        return tipoTelefone == null ? null : EnumTipoTelefone.getById(tipoTelefone);
    }

}
