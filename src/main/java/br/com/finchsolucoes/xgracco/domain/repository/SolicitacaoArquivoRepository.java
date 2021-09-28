package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Arquivo;
import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoArquivo;
import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Thiago Fogar
 * @since 2.2.5
 */
@Repository
public interface SolicitacaoArquivoRepository extends JpaRepository<SolicitacaoArquivo, Long>,SolicitacaoArquivoJpaRepository  {


}
