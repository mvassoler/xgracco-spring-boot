package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Caso;
import br.com.finchsolucoes.xgracco.domain.entity.CasoProcesso;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface CasoProcessoJpaRepository {
    List<CasoProcesso> find(Caso caso, Query<CasoProcesso> query);

    long count(Caso caso, Query<CasoProcesso> query);

    Optional<CasoProcesso> findById(Long id);

    Optional<CasoProcesso> findById(Caso caso, Long id);

    Optional<CasoProcesso> findByProcesso(Caso caso, Long idProcesso);

    void create(CasoProcesso casoProcesso);

    void update(CasoProcesso casoProcesso);

    void removeById(Long id, boolean recursivo);
}
