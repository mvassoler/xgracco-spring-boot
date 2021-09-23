package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumContratoClausulaTipo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumContratoClausulaTipoConverter implements AttributeConverter<EnumContratoClausulaTipo, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumContratoClausulaTipo contratoClausulaTipo) {
        return contratoClausulaTipo == null ? null : contratoClausulaTipo.getId();
    }

    @Override
    public EnumContratoClausulaTipo convertToEntityAttribute(Integer contratoClausulaTipo) {
        return contratoClausulaTipo == null ? null : EnumContratoClausulaTipo.getById(contratoClausulaTipo);
    }
}
