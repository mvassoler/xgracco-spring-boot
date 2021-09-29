package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDespesaArquivo;
import br.com.finchsolucoes.xgracco.domain.entity.QProcessoDespesaArquivo;
import br.com.finchsolucoes.xgracco.domain.entity.QProcessoDespesas;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.repository.ProcessoDespesaArquivoJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProcessoDespesaArquivoRepositoryImpl extends AbstractJpaRepository<ProcessoDespesaArquivo, Long> implements ProcessoDespesaArquivoJpaRepository {


    public List<ProcessoDespesaArquivo> find(Query<ProcessoDespesaArquivo> query) {
        return null;
    }


    public long count(Query<ProcessoDespesaArquivo> query) {
        return 0;
    }


    public Optional<List<Long>> findCaminhosArquivosByIdsProcessosDespesas(List<Long> idsProcessosDespesas){
        final QProcessoDespesaArquivo qProcessoDespesaArquivo = QProcessoDespesaArquivo.processoDespesaArquivo;
        final QProcessoDespesas qProcessoDespesas = QProcessoDespesas.processoDespesas;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .selectDistinct(qProcessoDespesaArquivo.id)
                .from(qProcessoDespesaArquivo)
                .join(qProcessoDespesaArquivo.processoDespesas, qProcessoDespesas)
                .where(qProcessoDespesas.id.in(idsProcessosDespesas))
                .fetch());
    }


    public Optional<List<String>> findCaminhosArquivosByIds(List<Long> idsProcessoDespesaArquivo){
        final QProcessoDespesaArquivo qProcessoDespesaArquivo = QProcessoDespesaArquivo.processoDespesaArquivo;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .selectDistinct(qProcessoDespesaArquivo.caminho)
                .from(qProcessoDespesaArquivo)
                .where(qProcessoDespesaArquivo.id.in(idsProcessoDespesaArquivo))
                .fetch());
    }


}
