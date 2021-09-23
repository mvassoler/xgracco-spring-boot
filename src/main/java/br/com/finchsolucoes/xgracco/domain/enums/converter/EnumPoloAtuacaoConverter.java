package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumPoloAtuacao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by felipiberdun on 20/10/2016.
 */
@Converter(autoApply = true)
public class EnumPoloAtuacaoConverter implements AttributeConverter<EnumPoloAtuacao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumPoloAtuacao enumPoloAtuacao) {
        return enumPoloAtuacao == null ? null : enumPoloAtuacao.getId();
    }

    @Override
    public EnumPoloAtuacao convertToEntityAttribute(Integer enumPoloAtuacao) {
        return enumPoloAtuacao == null ? null : EnumPoloAtuacao.getById(enumPoloAtuacao);
    }

}
