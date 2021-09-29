package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDiretorio;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDocumento;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDocumentoArquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * Repositório da entidade ProcessoDocumentoArquivo.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public interface ProcessoDocumentoArquivoRepository extends JpaRepository<ProcessoDocumentoArquivo, Long>, ProcessoDocumentoArquivoJpaRepository {


}
