package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoDepositoJuizo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by felipiberdun on 27/10/2016.
 */
@Converter(autoApply = true)
public class EnumTipoDepositoJuizoConverter implements AttributeConverter<EnumTipoDepositoJuizo, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoDepositoJuizo enumTipoDepositoJuizo) {
        return enumTipoDepositoJuizo == null ? null : enumTipoDepositoJuizo.getId();
    }

    @Override
    public EnumTipoDepositoJuizo convertToEntityAttribute(Integer enumTipoMovimentoFinanceiro) {
        return enumTipoMovimentoFinanceiro == null ? null : EnumTipoDepositoJuizo.getById(enumTipoMovimentoFinanceiro);
    }

}
