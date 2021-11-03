package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ParametroGlobal;
import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParametroGlobalRepository extends JpaRepository<ParametroGlobal, Long> {


    List<ParametroGlobal> findByDescricaoOrderByDescricaoAsc(String descricao);

    ParametroGlobal findByChave(EnumParametro chave);

    List<ParametroGlobal> findByClasse(String clazzName);

}
