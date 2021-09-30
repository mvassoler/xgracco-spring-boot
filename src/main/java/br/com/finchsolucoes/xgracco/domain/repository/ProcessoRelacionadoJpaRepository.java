package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoRelacionado;

import java.util.List;
import java.util.Optional;

public interface ProcessoRelacionadoJpaRepository {

    void insertProcessoRelacionado(Long idProcesso, Long idRelacionado);

    /**
     * Busca na entidade {@link ProcessoRelacionado} filtrando por {@link Processo};
     *
     * @param processo entidade {@link Processo}.
     * @return um {@link Optional} de uma entidade {@link ProcessoRelacionado}.
     */
    List<ProcessoRelacionado> findByProcesso(Processo processo);

    /**
     * Busca uma entidade {@link ProcessoRelacionado} filtrando por {@link Processo}
     *
     * @param processo entidade {@link Processo}.
     * @param relacionado      entidade {@link Processo}.
     * @return um {@link Optional} de uma entidade {@link ProcessoRelacionado}.
     */
    Optional<ProcessoRelacionado> findOnlyProcessoRelacionadoByProcessoAndRelacionado(Processo processo, Processo relacionado);

}
