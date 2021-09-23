package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumPublicacaoSituacao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Jordano
 */
@Converter(autoApply = true)
public class EnumPublicacaoSituacaoConverter implements AttributeConverter<EnumPublicacaoSituacao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumPublicacaoSituacao situacao) {
        return situacao == null ? null : situacao.getId();
    }

    @Override
    public EnumPublicacaoSituacao convertToEntityAttribute(Integer situacao) {
        return situacao == null ? null : EnumPublicacaoSituacao.getById(situacao);
    }
}
