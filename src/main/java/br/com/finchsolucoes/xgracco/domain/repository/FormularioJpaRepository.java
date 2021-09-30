package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.Formulario;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface FormularioJpaRepository {
    List<Formulario> find(Query<Formulario> query);

    long count(Query<Formulario> query);

    Optional<Formulario> findPai(Formulario formulario);

    List<Formulario> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa);
}
