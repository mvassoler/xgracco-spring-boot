package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.UsuarioAcessoLog;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface UsuarioAcessoLogJpaRepository {

    List<UsuarioAcessoLog> find(Query<UsuarioAcessoLog> query);

    long count(Query<UsuarioAcessoLog> query);
}
