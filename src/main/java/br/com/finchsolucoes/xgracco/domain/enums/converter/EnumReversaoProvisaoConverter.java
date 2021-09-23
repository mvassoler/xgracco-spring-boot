package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumReversaoProvisao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by Jordano on 19/04/2018.
 */
@Converter(autoApply = true)
public class EnumReversaoProvisaoConverter implements AttributeConverter<EnumReversaoProvisao, Integer> {


    @Override
    public Integer convertToDatabaseColumn(EnumReversaoProvisao reversaoProvisao) {
        return reversaoProvisao == null ? null : reversaoProvisao.getId();

    }

    @Override
    public EnumReversaoProvisao convertToEntityAttribute(Integer reversaoProvisao) {
        return reversaoProvisao == null ? null : EnumReversaoProvisao.getById(reversaoProvisao);
    }
}
