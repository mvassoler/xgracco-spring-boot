package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumContratoClausulaValorSobre;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumContratoClausulaValorSobreConverter implements AttributeConverter<EnumContratoClausulaValorSobre, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumContratoClausulaValorSobre contratoClausulaValorSobre) {
        return contratoClausulaValorSobre == null ? null : contratoClausulaValorSobre.getId();
    }

    @Override
    public EnumContratoClausulaValorSobre convertToEntityAttribute(Integer contratoClausulaValorSobre) {
        return contratoClausulaValorSobre == null ? null : EnumContratoClausulaValorSobre.getById(contratoClausulaValorSobre);
    }
}
