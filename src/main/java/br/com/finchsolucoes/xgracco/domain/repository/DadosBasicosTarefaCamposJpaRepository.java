package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefaCampos;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DadosBasicosTarefaCamposJpaRepository {
    List<DadosBasicosTarefaCampos> find(Query<DadosBasicosTarefaCampos> query);

    long count(Query<DadosBasicosTarefaCampos> query);

    Optional<DadosBasicosTarefaCampos> findById(Long id);

    void create(DadosBasicosTarefaCampos dadosBasicosTarefaCampos);

    void delete(DadosBasicosTarefaCampos dadosBasicosTarefaCampos);

    void update(DadosBasicosTarefaCampos dadosBasicosTarefaCampos);

    void deleteByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa);

    List<DadosBasicosTarefaCampos> findUploadsByProcessos(Set<Processo> processos);
}
