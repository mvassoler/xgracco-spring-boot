package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumUf;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by felipiberdun on 11/01/2017.
 */
@Converter(autoApply = true)
public class EnumUfConverter implements AttributeConverter<EnumUf, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumUf uf) {
        return uf == null ? null : uf.getId();
    }

    @Override
    public EnumUf convertToEntityAttribute(Integer uf) {
        return uf == null ? null : EnumUf.getById(uf);
    }

}
