package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Papel;
import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPapel;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface PapelJpaRepository {
    List<Papel> find(Query<Papel> query);

    long count(Query<Papel> query);

    List<Pessoa> findByPapel(Papel papel);

    List<Papel> findPapelByPessoa(Pessoa pessoa);

    List<Papel> findAll();

    Papel findByCodigoInterno(EnumTipoPapel tipoPapel);

    Optional<Papel> findByTipoPapelAndSistema(EnumTipoPapel tipoPapel, boolean sistema);
}
