package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ParametroEspecifico;
import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParametroEspecificoRepository extends JpaRepository<ParametroEspecifico, Long> {

    ParametroEspecifico findByParametroChaveAndIdPessoa(EnumParametro chave, Long idPessoa);

    List<ParametroEspecifico> findByParametroClasseAndIdPessoa(String clazzName, Long idPessoa);

}
