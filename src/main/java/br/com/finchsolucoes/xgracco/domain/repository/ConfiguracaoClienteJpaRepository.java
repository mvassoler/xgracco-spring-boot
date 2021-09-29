package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ConfiguracaoCliente;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface ConfiguracaoClienteJpaRepository {
    List<ConfiguracaoCliente> find(Query<ConfiguracaoCliente> query);

    long count(Query<ConfiguracaoCliente> query);

    Optional<ConfiguracaoCliente> findById(Long id);
}
