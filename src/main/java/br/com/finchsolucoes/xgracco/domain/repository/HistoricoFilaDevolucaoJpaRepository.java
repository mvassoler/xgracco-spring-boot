package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.HistoricoFilaDevolucao;

import java.util.Optional;

public interface HistoricoFilaDevolucaoJpaRepository {
    Optional<HistoricoFilaDevolucao> findByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa);

    void removeByIdFila(Long idFila);

    void create(HistoricoFilaDevolucao historicoFilaDevolucao);

    void update(HistoricoFilaDevolucao historicoFilaDevolucao);
}
