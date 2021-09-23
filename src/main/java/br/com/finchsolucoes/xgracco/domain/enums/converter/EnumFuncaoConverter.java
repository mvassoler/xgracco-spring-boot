package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumFuncao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumFuncaoConverter implements AttributeConverter<EnumFuncao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumFuncao funcao) {
        return funcao == null ? null : funcao.getId();
    }

    @Override
    public EnumFuncao convertToEntityAttribute(Integer funcao) {
        return funcao == null ? null : EnumFuncao.getById(funcao);
    }
}
