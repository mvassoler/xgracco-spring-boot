package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoProcesso;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by felipiberdun on 10/01/2017.
 */
@Converter(autoApply = true)
public class EnumTipoProcessoConverter implements AttributeConverter<EnumTipoProcesso, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoProcesso tipoProcesso) {
        return tipoProcesso == null ? null : tipoProcesso.getId();
    }

    @Override
    public EnumTipoProcesso convertToEntityAttribute(Integer tipoProcesso) {
        return tipoProcesso == null ? null : EnumTipoProcesso.getById(tipoProcesso);
    }

}
