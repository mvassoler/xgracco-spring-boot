package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTratamentoPessoa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by felipiberdun on 11/01/2017.
 */
@Converter(autoApply = true)
public class EnumTratamentoPessoaConverter implements AttributeConverter<EnumTratamentoPessoa, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTratamentoPessoa tratamentoPessoa) {
        return tratamentoPessoa == null ? null : tratamentoPessoa.getId();
    }

    @Override
    public EnumTratamentoPessoa convertToEntityAttribute(Integer tratamentoPessoa) {
        return tratamentoPessoa == null ? null : EnumTratamentoPessoa.getById(tratamentoPessoa);
    }

}
