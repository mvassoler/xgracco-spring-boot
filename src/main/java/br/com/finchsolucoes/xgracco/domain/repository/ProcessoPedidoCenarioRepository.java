package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoPedidoCenarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessoPedidoCenarioRepository extends JpaRepository<ProcessoPedidoCenarios, Long>,ProcessoPedidoCenarioJpaRepository {




}