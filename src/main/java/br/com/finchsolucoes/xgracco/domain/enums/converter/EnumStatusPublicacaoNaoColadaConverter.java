package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusPublicacaoNaoColada;

import javax.persistence.AttributeConverter;

/**
 * @author guilhermecamargo
 */
public class EnumStatusPublicacaoNaoColadaConverter implements AttributeConverter<EnumStatusPublicacaoNaoColada, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumStatusPublicacaoNaoColada statusPubNaoColada) {
        return statusPubNaoColada == null ? null : statusPubNaoColada.getId();
    }

    @Override
    public EnumStatusPublicacaoNaoColada convertToEntityAttribute(Integer id) {
        return id == null ? null : EnumStatusPublicacaoNaoColada.getById(id);
    }
}