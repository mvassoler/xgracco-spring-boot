package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EnumInstanciaConverter implements AttributeConverter<EnumInstancia, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumInstancia instancia) {
        return instancia ==  null ? null : instancia.getId();
    }

    @Override
    public EnumInstancia convertToEntityAttribute(Integer instancia) {
        return instancia == null ? null : EnumInstancia.getById(instancia);
    }

}