package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.TemplateDiretorio;
import br.com.finchsolucoes.xgracco.domain.query.Query;


import java.util.List;

public interface TemplateDiretorioJpaRepository {

    List<TemplateDiretorio> findByModeloTemplate(Long idModeloTemplate);


    List<TemplateDiretorio> find(Query<TemplateDiretorio> query);

    long count(Query<TemplateDiretorio> query);
}
