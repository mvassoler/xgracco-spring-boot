package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.AtendimentoFilaPessoa;
import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.Fila;
import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AtendimentoFilaPessoaJpaRepository {
    Optional<AtendimentoFilaPessoa> findAtendimentoFilaByPessoa(Pessoa pessoa);

    Long countAtendimentoFilaByFila(Fila fila);

    void removePessoasFila(Long idFila);

    void removeById(Long id);

    Optional<AtendimentoFilaPessoa> findByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa);

    @Transactional
    void create(AtendimentoFilaPessoa atendimentoFilaPessoa);
}
