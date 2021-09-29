package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoParte;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface ProcessoParteJpaRepository {

    List<ProcessoParte> find(Query<ProcessoParte> query);

    List<ProcessoParte> findByProcesso(Processo processo);

    List<ProcessoParte> findAllRelations(ProcessoParte parte);
}
