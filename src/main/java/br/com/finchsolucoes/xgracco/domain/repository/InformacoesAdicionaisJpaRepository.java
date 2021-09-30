package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface InformacoesAdicionaisJpaRepository {
    List<InformacoesAdicionais> find(Query<InformacoesAdicionais> query);

    long count(Query<InformacoesAdicionais> query);

    List<InformacoesAdicionais> findByProcessoGarantia(Processo processo, ProcessoGarantia garantia);

    InformacoesAdicionais findByCampoProcesso(Campo campo, Processo processo);

    void removeByProcesso(Processo processo);

    void removeByPessoa(Pessoa pessoa);
}
