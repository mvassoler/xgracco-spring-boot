package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPessoa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoPessoaConverter implements AttributeConverter<EnumTipoPessoa, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoPessoa tipoPessoa) {
        return tipoPessoa == null ? null : tipoPessoa.getId();
    }

    @Override
    public EnumTipoPessoa convertToEntityAttribute(Integer tipoPessoa) {
        return tipoPessoa == null ? null : EnumTipoPessoa.getById(tipoPessoa);
    }
}
