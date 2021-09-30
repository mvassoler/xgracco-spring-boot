package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.SolicitacaoAndamentoLogStatus;

import javax.persistence.AttributeConverter;

/**
 * @author andrebaroni
 */
public class SolicitacaoAndamentoLogStatusConverter implements AttributeConverter<SolicitacaoAndamentoLogStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SolicitacaoAndamentoLogStatus statusPubNaoColada) {
        return statusPubNaoColada == null ? null : statusPubNaoColada.getId();
    }

    @Override
    public SolicitacaoAndamentoLogStatus convertToEntityAttribute(Integer id) {
        return id == null ? null : SolicitacaoAndamentoLogStatus.getById(id);
    }
}