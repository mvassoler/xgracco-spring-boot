package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Reparticao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by felipiberdun on 17/01/2017.
 */
public interface ReparticaoRepository extends JpaRepository<Reparticao, Long>,ReparticaoJpaRepository {


}
