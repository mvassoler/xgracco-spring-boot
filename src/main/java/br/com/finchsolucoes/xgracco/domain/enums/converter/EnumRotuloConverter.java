package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumRotulo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumRotuloConverter implements AttributeConverter<EnumRotulo, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumRotulo rotulo) {
        return rotulo == null ? null : rotulo.getId();
    }

    @Override
    public EnumRotulo convertToEntityAttribute(Integer rotulo) {
        return rotulo == null ? null : EnumRotulo.getById(rotulo);
    }
}
