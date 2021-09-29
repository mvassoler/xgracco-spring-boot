package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaDiretorio;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaDocumento;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PessoaDocumentoJpaRepository {
    List<PessoaDocumento> find(Query<PessoaDocumento> query);

    long count(Query<PessoaDocumento> query);

    Optional<PessoaDocumento> findByNomeAndDiretorio(String nome, Long idDiretorio);

    Optional<PessoaDocumento> findByIdAndPessoaAndDiretorio(Long idDocumento, Pessoa pessoa, PessoaDiretorio diretorio);

    void removeByDiretorios(Set<Long> diretorios);

    List<PessoaDocumento> findByPessoaAndIdProfile(Pessoa pessoa, Long idProfile);

    List<PessoaDocumento> findByPessoaAndDiretorio(Pessoa pessoa, PessoaDiretorio diretorio);

    String findCaminhoDocumentoById(Long idDocumento);
}
