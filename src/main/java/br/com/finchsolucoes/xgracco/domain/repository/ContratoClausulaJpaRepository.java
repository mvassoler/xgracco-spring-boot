package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ContratoClausula;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;

import java.util.List;

public interface ContratoClausulaJpaRepository {
    List<ContratoClausula> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa);
}
