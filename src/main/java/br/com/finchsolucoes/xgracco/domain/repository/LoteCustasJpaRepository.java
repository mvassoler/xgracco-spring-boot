package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.LoteCustas;
import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;

import java.util.Calendar;
import java.util.List;

public interface LoteCustasJpaRepository {
    List<LoteCustas> find(Query<LoteCustas> query);

    long count(Query<LoteCustas> query);

    List<LoteCustas> findByPeriodoCliente(String numeroLote, Integer statusLote, Calendar dataInicio, Calendar dataFim, Pessoa cliente, String sortProperty, Sorter.Direction sortDirection);
}
