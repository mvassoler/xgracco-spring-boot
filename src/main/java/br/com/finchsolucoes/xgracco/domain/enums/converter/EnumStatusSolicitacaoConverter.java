package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusSolicitacao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by felipiberdun on 20/10/2016.
 */
@Converter(autoApply = true)
public class EnumStatusSolicitacaoConverter implements AttributeConverter<EnumStatusSolicitacao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumStatusSolicitacao enumStatusSolicitacao) {
        return enumStatusSolicitacao == null ? null : enumStatusSolicitacao.getId();
    }

    @Override
    public EnumStatusSolicitacao convertToEntityAttribute(Integer enumStatusSolicitacao) {
        return enumStatusSolicitacao == null ? null : EnumStatusSolicitacao.getById(enumStatusSolicitacao);
    }

}
