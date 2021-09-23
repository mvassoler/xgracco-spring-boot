package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusPagamentoHonorario;

import javax.persistence.AttributeConverter;

public class EnumStatusPagamentoHonorarioConverter implements AttributeConverter<EnumStatusPagamentoHonorario, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumStatusPagamentoHonorario statusPagamento) {
        return statusPagamento == null ? null : statusPagamento.getId();
    }

    @Override
    public EnumStatusPagamentoHonorario convertToEntityAttribute(Integer statusPagamento) {
        return statusPagamento == null ? null : EnumStatusPagamentoHonorario.getById(statusPagamento);
    }
}