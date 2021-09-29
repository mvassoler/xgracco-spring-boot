package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório da entidade ProcessoUsuario
 * <p>
 * Created by Jordano on 13/07/2017.
 */
public interface ProcessoUsuarioRepository extends JpaRepository<ProcessoUsuario, Long>, ProcessoUsuarioJpaRepository {



}
