package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDespesas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

/**
 * Repositório da entidade ProcessoDespesas
 * <p>
 * Created by Raphael Moreira on 08/06/2018.
 */
@Repository
public interface ProcessoDespesasRepository extends JpaRepository<ProcessoDespesas, Long>,ProcessoDespesasJpaRepository {

}
