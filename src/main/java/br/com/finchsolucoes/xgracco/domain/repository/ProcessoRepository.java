package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.Tag;
import br.com.finchsolucoes.xgracco.domain.enums.EnumOrigemProcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repositório da entidade Processo.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long>, ProcessoJpaRepository {


}
