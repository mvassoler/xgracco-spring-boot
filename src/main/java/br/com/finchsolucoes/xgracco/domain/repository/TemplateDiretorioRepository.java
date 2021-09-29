package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.TemplateDiretorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateDiretorioRepository extends JpaRepository<TemplateDiretorio, Long>,TemplateDiretorioJpaRepository {



}
