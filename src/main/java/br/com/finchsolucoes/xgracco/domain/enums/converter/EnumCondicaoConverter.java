package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumCondicao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by renan on 04/10/16.
 */
@Converter(autoApply = true)
public class EnumCondicaoConverter implements AttributeConverter<EnumCondicao, String> {

    @Override
    public String convertToDatabaseColumn(EnumCondicao condicao) {
        return condicao == null ? null : condicao.getId();
    }

    @Override
    public EnumCondicao convertToEntityAttribute(String condicao) {
        return condicao == null ? null : EnumCondicao.getById(condicao);
    }
}
