package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoDestinatario;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoDestinatarioConverter implements AttributeConverter<EnumTipoDestinatario, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoDestinatario tipoDestinatario) {
        return tipoDestinatario == null ? null : tipoDestinatario.getId();
    }

    @Override
    public EnumTipoDestinatario convertToEntityAttribute(Integer tipoDestinatario) {
        return tipoDestinatario == null ? null : EnumTipoDestinatario.getById(tipoDestinatario);
    }
}
