package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CaixaLoteCustas;
import br.com.finchsolucoes.xgracco.domain.entity.LoteCustas;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;

import java.util.List;
import java.util.Optional;

public interface CaixaLoteCustasJpaRepository {
    List<CaixaLoteCustas> find(Query<CaixaLoteCustas> query);

    long count(Query<CaixaLoteCustas> query);

    List<CaixaLoteCustas> findByLote(LoteCustas loteCustas, String sortProperty, Sorter.Direction sortDirection);

    Optional<CaixaLoteCustas> findByIdCaixa(Long idCaixa);
}
