package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaDiretorio;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PessoaDiretorioJpaRepository {
    List<PessoaDiretorio> find(Query<PessoaDiretorio> query);

    long count(Query<PessoaDiretorio> query);

    Optional<PessoaDiretorio> findByNomeAndPessoa(String nomeDiretorio, Long idPessoa, Long idDiretorioPai);

    Optional<PessoaDiretorio> findByIdAndPessoa(Long idDiretorio, Pessoa pessoa);

    List<Long> findIdsRecursiveByProfile(Long idProfilePai);

    List<PessoaDiretorio> findByIds(Set<Long> profiles);

    void removeByIds(Set<Long> diretorios);

    List<PessoaDiretorio> findByPessoa(Pessoa pessoa, Long idProfile);

    Long countDiretoriosByIdPessoa(Long idPessoa);

    List<PessoaDiretorio> findByIdPessoa(Long idPessoa);
}
