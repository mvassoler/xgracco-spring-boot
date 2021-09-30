package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ImportacaoPlanilha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportacaoPlanilhaRepository extends JpaRepository<ImportacaoPlanilha, Long>, ImportacaoPlanilhaJpaRepository {

}
