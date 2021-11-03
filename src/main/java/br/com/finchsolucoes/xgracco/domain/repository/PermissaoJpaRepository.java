package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Permissao;

import java.util.List;
import java.util.Optional;

public interface PermissaoJpaRepository {

    List<Permissao> findAll();

    List<Permissao> findByRoot();

    Optional<Permissao> findByCodigo(String codigo);
}
