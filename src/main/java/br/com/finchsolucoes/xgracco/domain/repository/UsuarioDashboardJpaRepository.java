package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.entity.UsuarioDashboard;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import java.util.List;

public interface UsuarioDashboardJpaRepository {

    UsuarioDashboard findUsuario(Usuario usuario);

    List<UsuarioDashboard> find(Query<UsuarioDashboard> query);

    long count(Query<UsuarioDashboard> query);






}
