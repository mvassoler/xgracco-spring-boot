package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.SistemaVirtual;

import java.util.List;

public interface SistemaVirtualJpaRepository {
    List<SistemaVirtual> findByDescricao(String sistemaDescricao);

    List<SistemaVirtual> findAllCache();
}
