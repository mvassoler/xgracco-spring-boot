package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface FilaJpaRepository {
    List<Fila> find(Query<Fila> query);

    long count(Query<Fila> query);

    Fila findDevolucaoByEsteira(Esteira esteira);

    List<Fila> findFilaByEsteira(Esteira esteira);

    List<Fila> findFilaByTarefaAndProcesso(FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Processo processo);

    List<Fila> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa);

    List<Fila> findFilaAndEsteira(Long idFila, String descricaoEsteira, String descricaoFila, Long idCarteira, Boolean ativo, String descricaoTag, Boolean todasAsFilas, Long idUsuario, boolean somenteFilaAtendimentos);

    List<Fila> findByFluxoTrabalhoTarefaCarteira(FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Carteira carteira);
}
