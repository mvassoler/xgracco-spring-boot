package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CaixaLoteCustas;
import br.com.finchsolucoes.xgracco.domain.entity.CaixaLoteDespesas;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface CaixaLoteDespesasJpaRepository {
    List<CaixaLoteDespesas> find(Query<CaixaLoteDespesas> query);

    long count(Query<CaixaLoteDespesas> query);

    List<CaixaLoteDespesas> findByCaixa(CaixaLoteCustas caixaLoteCustas);
}
