package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.LoteCustas;
import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusLoteCustas;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.util.Calendar;

/**
 * Filtro de LoteCustas.
 *
 * @author Raphael Moreira
 * @since 4.0.4
 */
public class LoteCustasFilter implements Filter<LoteCustas> {
    private String numeroLote;
    private Calendar dataLoteInicio;
    private Calendar dataLoteFim;
    private EnumStatusLoteCustas statusLoteCustas;
    private Pessoa cliente;

    public LoteCustasFilter(String numeroLote, Calendar dataLoteInicio, Calendar dataLoteFim, EnumStatusLoteCustas statusLoteCustas, Pessoa cliente) {
        this.numeroLote = numeroLote;
        this.dataLoteInicio = dataLoteInicio;
        this.dataLoteFim = dataLoteFim;
        this.statusLoteCustas = statusLoteCustas;
        this.cliente = cliente;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public EnumStatusLoteCustas getStatusLoteCustas() {
        return statusLoteCustas;
    }

    public Pessoa getCliente() {
        return cliente;
    }

    public Calendar getDataLoteInicio() {
        return dataLoteInicio;
    }

    public Calendar getDataLoteFim() {
        return dataLoteFim;
    }
}
