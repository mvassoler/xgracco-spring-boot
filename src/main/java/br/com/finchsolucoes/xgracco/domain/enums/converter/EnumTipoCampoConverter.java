package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoCampo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoCampoConverter implements AttributeConverter<EnumTipoCampo, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoCampo tipoCampo) {
        return tipoCampo == null ? null : tipoCampo.getId();
    }

    @Override
    public EnumTipoCampo convertToEntityAttribute(Integer tipoCampo) {
        return tipoCampo == null ? null : EnumTipoCampo.getById(tipoCampo);
    }
}
