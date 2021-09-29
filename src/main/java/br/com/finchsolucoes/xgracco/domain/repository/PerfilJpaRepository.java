package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Perfil;
import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PerfilJpaRepository {
    List<Perfil> findAll();

    List<Usuario> findByUsuario(Perfil perfil);

    List<Perfil> find(Query<Perfil> query);

    long count(Query<Perfil> query);

    Optional<Perfil> findById(Long id);

    List<Perfil> findByNome(Set<String> nomes);
}
