package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.repository.AcaoQueryDsl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AcaoRepositoryImpl implements AcaoQueryDsl {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Acao> find(String nomeInicial, String nomeFinal) {
        var jpql = "from Acao where descricao between :nomeInicial and :nomeFinal";

        return entityManager.createQuery(jpql, Acao.class)
                .setParameter("nomeInicial", nomeInicial)
                .setParameter("nomeFinal", nomeFinal)
                .getResultList();

    }

    @Override
    public List<Acao> find(Query<Acao> query) {
        return null;
    }

    @Override
    public List<Acao> findAllCache() {
        return null;
    }

    @Override
    public long count(Query<Acao> query) {
        return 0;
    }
}
