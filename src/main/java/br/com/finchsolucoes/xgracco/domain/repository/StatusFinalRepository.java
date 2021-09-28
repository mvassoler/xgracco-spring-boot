package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.StatusFinal;
import br.com.finchsolucoes.xgracco.domain.entity.TarefaStatusFinal;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório da entidade StatusFinal.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public interface StatusFinalRepository extends JpaRepository<StatusFinal, Long>, StatusFinalJpaRepository {


}
