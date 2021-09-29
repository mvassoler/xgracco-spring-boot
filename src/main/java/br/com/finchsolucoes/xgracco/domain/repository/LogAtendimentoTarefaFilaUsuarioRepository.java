package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.LogAtendimentoTarefaFilaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAtendimentoTarefaFilaUsuarioRepository extends JpaRepository<LogAtendimentoTarefaFilaUsuario, Long>, LogAtendimentoTarefaFilaUsuarioJpaRepository {

}
