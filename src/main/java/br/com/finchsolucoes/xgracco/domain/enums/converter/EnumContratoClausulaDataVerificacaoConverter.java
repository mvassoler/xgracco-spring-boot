package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumContratoClausulaDataVerificacao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumContratoClausulaDataVerificacaoConverter implements AttributeConverter<EnumContratoClausulaDataVerificacao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumContratoClausulaDataVerificacao contratoClausulaDataVerificacao) {
        return contratoClausulaDataVerificacao == null ? null : contratoClausulaDataVerificacao.getId();
    }

    @Override
    public EnumContratoClausulaDataVerificacao convertToEntityAttribute(Integer contratoClausulaDataVerificacao) {
        return contratoClausulaDataVerificacao == null ? null : EnumContratoClausulaDataVerificacao.getById(contratoClausulaDataVerificacao);
    }
}
