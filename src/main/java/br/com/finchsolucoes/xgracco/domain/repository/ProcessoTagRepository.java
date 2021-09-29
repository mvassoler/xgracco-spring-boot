package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoTag;
import br.com.finchsolucoes.xgracco.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository da entidade ProcessoTag
 *
 * @author guilhermecamargo
 */
public interface ProcessoTagRepository extends JpaRepository<ProcessoTag, Long>,ProcessoTagJpaRepository {


}
