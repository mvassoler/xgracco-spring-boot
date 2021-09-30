package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDespesaArquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório dos arquivos das despesas
 *
 * @since 2.2.9
 * @author paulo.marcon
 */
@Repository
public interface ProcessoDespesaArquivoRepository extends JpaRepository<ProcessoDespesaArquivo, Long>,ProcessoDespesaArquivoJpaRepository {



}
