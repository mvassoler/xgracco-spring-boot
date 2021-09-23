package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumCampoVariavel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumCampoVariavelConverter implements AttributeConverter<EnumCampoVariavel, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumCampoVariavel campoVariavel) {
        return campoVariavel == null ? null : campoVariavel.getId();
    }

    @Override
    public EnumCampoVariavel convertToEntityAttribute(Integer campoVariavel) {
        return campoVariavel == null ? null : EnumCampoVariavel.getById(campoVariavel);
    }
}
