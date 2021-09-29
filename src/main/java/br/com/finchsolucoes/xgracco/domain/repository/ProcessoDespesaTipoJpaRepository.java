package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDespesaTipo;

import java.util.List;

public interface ProcessoDespesaTipoJpaRepository {

    List<ProcessoDespesaTipo> findByNeIdAndDescricao(Long id, String descricao);
}
