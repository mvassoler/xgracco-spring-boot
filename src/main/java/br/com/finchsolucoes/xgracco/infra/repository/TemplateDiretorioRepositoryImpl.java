package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QTemplateDiretorio;
import br.com.finchsolucoes.xgracco.domain.entity.TemplateDiretorio;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.repository.TemplateDiretorioJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.TemplateDiretorioRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TemplateDiretorioRepositoryImpl extends AbstractJpaRepository<TemplateDiretorio, Long> implements TemplateDiretorioJpaRepository {


    public List<TemplateDiretorio> findByModeloTemplate(Long idModeloTemplate) {

        QTemplateDiretorio qTemplateDiretorio = QTemplateDiretorio.templateDiretorio1;

        return new JPAQueryFactory(entityManager)
                .select(qTemplateDiretorio)
                .from(qTemplateDiretorio)
                .where(qTemplateDiretorio.modeloTemplate.id.eq(idModeloTemplate))
                .where(qTemplateDiretorio.templateDiretorio.isNull())
                .fetch();

    }


    public List<TemplateDiretorio> find(Query<TemplateDiretorio> query) {
        return null;
    }


    public long count(Query<TemplateDiretorio> query) {
        return 0;
    }

}
