package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumSistemaExternoUsuario;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Classe responsável por realizar a conversão do Enum {@link EnumSistemaExternoUsuario} para ser persistido na base de dados
 *
 * @author Raphael Moreira
 */
@Converter(autoApply = true)
public class EnumSistemaExternoUsuarioConverter implements AttributeConverter<EnumSistemaExternoUsuario, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumSistemaExternoUsuario enumSistemaExternoUsuario) {
        return enumSistemaExternoUsuario == null ? null : enumSistemaExternoUsuario.getId();
    }

    @Override
    public EnumSistemaExternoUsuario convertToEntityAttribute(Integer enumSistemaExternoUsuario) {
        return enumSistemaExternoUsuario == null ? null : EnumSistemaExternoUsuario.getById(enumSistemaExternoUsuario);
    }

}
