package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoAndamentos;

import java.util.Collection;

public interface ProcessoAndamentosJpaRepository {
    Collection<ProcessoAndamentos> findByProcesso(Processo processo);
}
