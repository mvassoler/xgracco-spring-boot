package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumProcessoInformacao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumProcessoInformacaoConverter implements AttributeConverter<EnumProcessoInformacao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumProcessoInformacao processoInformacao) {
        return processoInformacao == null ? null : processoInformacao.getId();
    }

    @Override
    public EnumProcessoInformacao convertToEntityAttribute(Integer processoInformacao) {
        return processoInformacao == null ? null : EnumProcessoInformacao.getById(processoInformacao);
    }
}
