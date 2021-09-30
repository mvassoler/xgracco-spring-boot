package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PessoaDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaDocumentoRepository extends JpaRepository<PessoaDocumento, Long>, PessoaDocumentoJpaRepository {

}
