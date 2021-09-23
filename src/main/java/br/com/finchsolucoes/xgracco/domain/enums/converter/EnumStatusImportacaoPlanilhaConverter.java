package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusImportacaoPlanilha;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EnumStatusImportacaoPlanilhaConverter implements AttributeConverter<EnumStatusImportacaoPlanilha, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumStatusImportacaoPlanilha enumStatusImportacaoPlanilha) {
        return enumStatusImportacaoPlanilha ==  null ? null : enumStatusImportacaoPlanilha.getId();
    }

    @Override
    public EnumStatusImportacaoPlanilha convertToEntityAttribute(Integer instancia) {
        return instancia == null ? null : EnumStatusImportacaoPlanilha.getById(instancia);
    }
}
