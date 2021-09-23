package br.com.finchsolucoes.xgracco.domain.enums.converter;


import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusPagamentoDespesas;

import javax.persistence.AttributeConverter;

/**
 * @author guilhermecamargo
 */
public class EnumStatusPagamentoDespesasConverter implements AttributeConverter<EnumStatusPagamentoDespesas, Integer> {

@Override
public Integer convertToDatabaseColumn(EnumStatusPagamentoDespesas statusPagamento) {
        return statusPagamento == null ? null : statusPagamento.getId();
        }

@Override
public EnumStatusPagamentoDespesas convertToEntityAttribute(Integer statusPagamento) {
        return statusPagamento == null ? null : EnumStatusPagamentoDespesas.getById(statusPagamento);
        }
    }
