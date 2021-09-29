package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Escritorio;
import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EscritorioJpaRepository {
    List<Escritorio> find(Query<Escritorio> query);

    List<Escritorio> findAllCache();

    long count(Query<Escritorio> query);

    List<Escritorio> findAll();

    List<Escritorio> findEscritoriosRelacionadosByUsuario(Usuario usuario);

    void update(Escritorio escritorio);

    List<Escritorio> findByDescricao(String descricao);
}
