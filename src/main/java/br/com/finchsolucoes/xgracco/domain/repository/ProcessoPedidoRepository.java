package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PedidoIndice;
import br.com.finchsolucoes.xgracco.domain.entity.PedidoJuros;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProcessoPedidoRepository extends JpaRepository<ProcessoPedido, Long>,ProcessoPedidoJpaRepository {




}
