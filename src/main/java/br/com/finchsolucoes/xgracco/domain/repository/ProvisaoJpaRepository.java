package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Provisao;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.Calendar;
import java.util.List;

public interface ProvisaoJpaRepository {
    Provisao retornarProvisaoMesAno(Integer mes, Integer ano);

    List<Provisao> retornarValoresProvisionamento(Calendar dataInicio, Calendar dataFim, Boolean baseAtiva);
    List<Provisao> find(Query<Provisao> query);
    long count(Query<Provisao> query);
}
