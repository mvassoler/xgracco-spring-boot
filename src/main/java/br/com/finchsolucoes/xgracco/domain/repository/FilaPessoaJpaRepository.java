package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Fila;
import br.com.finchsolucoes.xgracco.domain.entity.FilaPessoa;

import java.util.List;
import java.util.Optional;

public interface FilaPessoaJpaRepository {
    List<FilaPessoa> findByFila(Fila fila);

    List<FilaPessoa> findByPessoa(Long idPessoa);

    Optional<FilaPessoa> findByPessoaAtivo(Long idPessoa);

    Optional<List<FilaPessoa>> findByPessoaAndEsteira(Long idPessoa, Long idEsteira);

    Optional<FilaPessoa> findByFilaAndPessoa(Long idFila, Long idPessoa);

    void update(Boolean status, Long idFila, Long idPessoa);

    Long countRecursosAtivosFila(Long idFila);

    Long countRecursosFila(Long idFila);

    List<FilaPessoa> consultaRecursosAtivosFila(Long idFila);
}
