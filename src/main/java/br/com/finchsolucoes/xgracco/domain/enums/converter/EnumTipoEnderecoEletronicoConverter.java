package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoEnderecoEletronico;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoEnderecoEletronicoConverter implements AttributeConverter<EnumTipoEnderecoEletronico, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoEnderecoEletronico tipoEnderecoEletronico) {
        return tipoEnderecoEletronico == null ? null : tipoEnderecoEletronico.getId();
    }

    @Override
    public EnumTipoEnderecoEletronico convertToEntityAttribute(Integer tipoEnderecoEletronico) {
        return tipoEnderecoEletronico == null ? null : EnumTipoEnderecoEletronico.getById(tipoEnderecoEletronico);
    }
}
