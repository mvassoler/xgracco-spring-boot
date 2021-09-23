package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumSexo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumSexoConverter implements AttributeConverter<EnumSexo, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumSexo sexo) {
        return sexo == null ? null : sexo.getId();
    }

    @Override
    public EnumSexo convertToEntityAttribute(Integer sexo) {
        return sexo == null ? null : EnumSexo.getById(sexo);
    }
}
