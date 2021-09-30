package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumImportacao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EnumImportacaoConverter implements AttributeConverter<EnumImportacao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumImportacao enumImportacao) {
        return enumImportacao ==  null ? null : enumImportacao.getId();
    }

    @Override
    public EnumImportacao convertToEntityAttribute(Integer instancia) {
        return instancia == null ? null : EnumImportacao.getById(instancia);
    }
}
