package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CaixaLoteCustas;
import br.com.finchsolucoes.xgracco.domain.entity.LoteCustas;
import br.com.finchsolucoes.xgracco.domain.entity.LoteCustasItem;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDespesas;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;

import java.util.List;

public interface LoteCustasItemJpaRepository {
    List<LoteCustasItem> find(Query<LoteCustasItem> query);

    long count(Query<LoteCustasItem> query);

    List<ProcessoDespesas> findByLote(LoteCustas loteCustas, Boolean semCaixa, String sortProperty, Sorter.Direction sortDirection, Long page);

    List<ProcessoDespesas> findByIdLista(List<LoteCustasItem> loteCustasItemList);

    List<ProcessoDespesas> findByCaixa(CaixaLoteCustas caixaLoteCustas, String sortProperty, Sorter.Direction sortDirection, Long page);

    LoteCustasItem findByDespesa(ProcessoDespesas processoDespesas);
}
