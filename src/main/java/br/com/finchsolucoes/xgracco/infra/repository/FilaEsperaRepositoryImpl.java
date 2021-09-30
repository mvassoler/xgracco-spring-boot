package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Fila;
import br.com.finchsolucoes.xgracco.domain.entity.FilaEspera;
import br.com.finchsolucoes.xgracco.domain.entity.QFilaEspera;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.repository.FilaEsperaJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilaEsperaRepositoryImpl extends AbstractJpaRepository<FilaEspera, Long> implements  FilaEsperaJpaRepository {
    @Override
    public List<FilaEspera> find(Query<FilaEspera> query) {
        return null;
    }

    @Override
    public List<FilaEspera> findFila(Fila fila) {

        QFilaEspera qFilaEspera =  QFilaEspera.filaEspera;

        return new JPAQueryFactory(entityManager)
                .select(
                        QFilaEspera.create(
                                qFilaEspera.id,
                                qFilaEspera.filaAtivaEspera
                        )
                ).from(qFilaEspera)
                .where(qFilaEspera.filaAtivaEspera.eq(fila))
                .fetch();

    }

    @Override
    public long count(Query<FilaEspera> query) {
        return 0;
    }

}
