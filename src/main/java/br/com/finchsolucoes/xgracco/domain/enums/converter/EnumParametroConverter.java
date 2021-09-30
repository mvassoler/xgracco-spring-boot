package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumParametroConverter implements AttributeConverter<EnumParametro, String> {

    @Override
    public String convertToDatabaseColumn(EnumParametro parametro) {
        return parametro == null ? null : parametro.getId();
    }

    @Override
    public EnumParametro convertToEntityAttribute(String parametro) {
        return parametro == null ? null : EnumParametro.getById(parametro);
    }
}
