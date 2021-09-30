package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.entity.GrupoAgendamento;
import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface GrupoAgendamentoJpaRepository {
    List<GrupoAgendamento> find(Query<GrupoAgendamento> query);

    long count(Query<GrupoAgendamento> query);

    GrupoAgendamento findByIdFetchAgendamentoTarefa(Long idGrupo);

    List<GrupoAgendamento> findByCarteiraArea(Carteira carteira, EnumArea area);
}
