package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Escritorio;
import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.entity.UsuarioEscritorio;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioEscritorioJpaRepository {
    List<UsuarioEscritorio> find(Usuario usuario, Query<UsuarioEscritorio> usuarioEscritorio);

    Long count(Usuario usuario, Query<UsuarioEscritorio> usuarioEscritorio);

    Optional<Escritorio> findPrincipal(Usuario usuario);

    List<UsuarioEscritorio> findByUsuario(Usuario usuario);

    void create(UsuarioEscritorio usuarioEscritorio);

    void remove (UsuarioEscritorio usuarioEscritorio);

    void setPrincipal(Usuario usuario, Escritorio escritorio, Boolean principal);

    void clearPrincipal(Usuario usuario);

    void clearByEscritorio(Escritorio escritorio);

    Optional<UsuarioEscritorio> findByUsuarioEscritorio(UsuarioEscritorio usuarioEscritorio);

    List<UsuarioEscritorio> findByEscritorio(Long idEscritorio);

    long countByUsuario(Long idUsuario);
}
