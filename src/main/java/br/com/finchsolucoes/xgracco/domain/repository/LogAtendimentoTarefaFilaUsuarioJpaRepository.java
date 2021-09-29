package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.Fila;
import br.com.finchsolucoes.xgracco.domain.entity.LogAtendimentoTarefaFilaUsuario;
import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.enums.EnumAcaoLogAtendimentoTarefa;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface LogAtendimentoTarefaFilaUsuarioJpaRepository {
    List<LogAtendimentoTarefaFilaUsuario> find(Query<LogAtendimentoTarefaFilaUsuario> query);

    long count(Query<LogAtendimentoTarefaFilaUsuario> query);

    Optional<LogAtendimentoTarefaFilaUsuario> find(DadosBasicosTarefa dbt, Usuario usuario, Fila fila, EnumAcaoLogAtendimentoTarefa acao);
}
