package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumSistemaVirtual;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by felipiberdun on 11/01/2017.
 */
@Converter(autoApply = true)
public class EnumSistemaVirtualConverter implements AttributeConverter<EnumSistemaVirtual, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumSistemaVirtual sistemaVirtual) {
        return sistemaVirtual == null ? null : sistemaVirtual.getId();
    }

    @Override
    public EnumSistemaVirtual convertToEntityAttribute(Integer sistemaVirtual) {
        return sistemaVirtual == null ? null : EnumSistemaVirtual.getById(sistemaVirtual);
    }

}
