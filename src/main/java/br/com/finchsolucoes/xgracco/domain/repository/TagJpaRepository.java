package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Fila;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagJpaRepository {


    Map<Tag, Long> findByProcessos(List<Processo> processos);

    void createProcessos(Tag tag, List<Processo> processos);

    void removeProcessos(Tag tag, List<Processo> processos);

    List<Tag> findByFila(Fila fila);

    List<Tag> findByAtendimentoFila(Fila fila);

    List<Tag> findByProcesso(Processo processo);

    List<Tag> findAllCache();

    Optional<Tag> findOnlyTagByNome(String nome);
}
