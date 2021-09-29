package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long>,PublicacaoJpaRepository {


}
