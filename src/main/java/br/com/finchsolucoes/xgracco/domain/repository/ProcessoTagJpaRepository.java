package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoTag;
import br.com.finchsolucoes.xgracco.domain.entity.Tag;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface ProcessoTagJpaRepository {

    //TODO Descomentar quando criar os DTOS
    //Map<Tag, Long> findByProcessoPesquisa(ProcessoConsultaDTO processoConsultaDTO);

    /**
     * Busca uma entidade {@link ProcessoTag} filtrando por {@link Processo} e {@link Tag};
     *
     * @param processo entidade {@link Processo}.
     * @param tag      entidade {@link Tag}.
     * @return um {@link Optional} de uma entidade {@link ProcessoTag}.
     */
    Optional<ProcessoTag> findOnlyProcessoTagByProcessoAndTag(Processo processo, Tag tag);

    void insertProcessoTag(Long idProcesso, Long idTag);

    /**
     * Busca uma entidade {@link ProcessoTag} filtrando por {@link Processo};
     *
     * @param processo entidade {@link Processo}.
     * @return um {@link Optional} de uma entidade {@link ProcessoTag}.
     */
    List<ProcessoTag> findByProcesso(Processo processo);

    List<ProcessoTag> find(Query<ProcessoTag> query);

    long count(Query<ProcessoTag> query);

}
