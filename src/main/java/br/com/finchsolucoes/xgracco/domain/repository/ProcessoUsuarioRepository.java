package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da entidade ProcessoUsuario
 * <p>
 * Created by Jordano on 13/07/2017.
 */
@Repository
public interface ProcessoUsuarioRepository extends JpaRepository<ProcessoUsuario, Long>, ProcessoUsuarioJpaRepository {



}
