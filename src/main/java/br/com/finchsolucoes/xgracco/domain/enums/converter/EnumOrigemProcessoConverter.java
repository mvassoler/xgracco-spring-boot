package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumOrigemProcesso;

import javax.persistence.AttributeConverter;

/**
 * @author guilhermecamargo
 */
public class EnumOrigemProcessoConverter implements AttributeConverter<EnumOrigemProcesso, Integer> {
    @Override
    public Integer convertToDatabaseColumn(EnumOrigemProcesso origemProcesso) {
        return origemProcesso == null ? null : origemProcesso.getId();
    }

    @Override
    public EnumOrigemProcesso convertToEntityAttribute(Integer id) {
        return id == null ? null : EnumOrigemProcesso.getById(id);
    }
}
