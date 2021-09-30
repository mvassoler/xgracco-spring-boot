package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Campo;
import br.com.finchsolucoes.xgracco.domain.entity.Formulario;
import br.com.finchsolucoes.xgracco.domain.entity.TarefaStatusFinal;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CampoJpaRepository {
    List<Campo> find(Query<Campo> query);

    long count(Query<Campo> query);

    List<Campo> findByFormularios(Collection<Formulario> formularios, Query<Campo> query);

    long countByFormularios(Collection<Formulario> formularios, Query<Campo> query);

    Optional<Campo> findByFormularioAndId(Formulario formulario, Long id);

    List<Campo> findByTarefaStatusFinal(TarefaStatusFinal tarefaStatusFinal);

    Optional<Campo> findByFormularioAndCampoPaiAndOpcaoPai(Long idFormulario, Long idCampo, Long idCampoLista);

    List<Campo> findByGrupo(Long idGrupo);

    List<Campo> findNaoPreenchidosByGrupoAndPreCadastroProcesso(Long idGrupo, Long idPreCadastroProcesso);
}
