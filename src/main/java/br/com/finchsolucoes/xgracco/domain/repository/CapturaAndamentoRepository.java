package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CapturaAndamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapturaAndamentoRepository extends JpaRepository<CapturaAndamento, Long>, CapturaAndamentoJpaRepository {
}
