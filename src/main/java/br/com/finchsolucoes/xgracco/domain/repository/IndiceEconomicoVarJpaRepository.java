package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.IndiceEconomico;
import br.com.finchsolucoes.xgracco.domain.entity.IndiceEconomicoVar;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface IndiceEconomicoVarJpaRepository {
    List<IndiceEconomicoVar> findVariaveis(IndiceEconomico indiceEconomico);

    void create(IndiceEconomico indiceEconomico, List<IndiceEconomicoVar> indicesEconomicosVar);

    List<IndiceEconomicoVar> find(Query<IndiceEconomicoVar> query);

    List<IndiceEconomicoVar> findByData(IndiceEconomicoVar indiceEconomicoVar);

    void deleteByIndiceEconomico(IndiceEconomico indiceEconomico);

    List<IndiceEconomicoVar> findByIndiceAndData(IndiceEconomico indiceEconomico, Calendar dataInicio, Calendar dataFim);

    Optional<IndiceEconomicoVar> findLastIndiceByIndice(Long id);

    Optional<IndiceEconomicoVar> findByIndiceAndDataInicio(IndiceEconomico indiceEconomico, Calendar dataInicio);

    BigDecimal getFatorSumIndiceEntreDatas(Calendar dataInicio, Calendar dataFim, IndiceEconomico indice);

    List<BigDecimal> findMonthlyValuesByIndiceBetweenDates(Calendar dataInicio, Calendar dataFim, IndiceEconomico indiceEconomico);

    long count(Query<IndiceEconomicoVar> query);

    long countVarByInidice(IndiceEconomico indiceEconomico);
}
