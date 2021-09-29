package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CalculoPrecificacaoExecucaoLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculoPrecificacaoExecucaoLogRepository extends JpaRepository<CalculoPrecificacaoExecucaoLog, Long>, CalculoPrecificacaoExecucaoLogJpaRepository {

}
