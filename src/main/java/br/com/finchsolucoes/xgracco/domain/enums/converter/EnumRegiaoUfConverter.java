package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumRegiaoUf;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumRegiaoUfConverter implements AttributeConverter<EnumRegiaoUf, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumRegiaoUf regiaoUf) {
        return regiaoUf == null ? null : regiaoUf.getId();
    }

    @Override
    public EnumRegiaoUf convertToEntityAttribute(Integer regiaoUf) {
        return regiaoUf == null ? null : EnumRegiaoUf.getById(regiaoUf);
    }
}
