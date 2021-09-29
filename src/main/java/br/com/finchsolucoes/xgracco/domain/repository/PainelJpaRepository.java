package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.entity.Painel;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.legacy.beans.views.PainelView;

import java.util.List;

public interface PainelJpaRepository {
    List<Painel> find(Query<Painel> query);

    long count(Query<Painel> query);

    List<PainelView> calcularTarefasSLA(List<String> idsTarefa, Painel painel);

    List<Painel> findPaineisByCarteira(List<Carteira> carteiras);
}
