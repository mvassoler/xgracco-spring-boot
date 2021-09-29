package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ConfiguracaoCliente;
import br.com.finchsolucoes.xgracco.domain.entity.QConfiguracaoCliente;
import br.com.finchsolucoes.xgracco.domain.entity.QEscritorio;
import br.com.finchsolucoes.xgracco.domain.entity.QPessoa;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.repository.ConfiguracaoClienteJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jordano on 05/06/2018.
 */
@Repository
public class ConfiguracaoClienteRepositoryImpl extends AbstractJpaRepository<ConfiguracaoCliente, Long> implements ConfiguracaoClienteJpaRepository {


    @Override
    public List<ConfiguracaoCliente> find(Query<ConfiguracaoCliente> query) {
        return null;
    }

    @Override
    public long count(Query<ConfiguracaoCliente> query) {
        return 0;
    }

    @Override
    public Optional<ConfiguracaoCliente> findById(Long id){
        final QConfiguracaoCliente qConfiguracaoCliente = QConfiguracaoCliente.configuracaoCliente;
        final QEscritorio qEscritorioPlanoEconomico = new QEscritorio("qEscritorioPlanoEconomico");
        final QPessoa qPessoaPlanoEconomico = new QPessoa("qPessoaPlanoEconomico");
        final QEscritorio qEscritorioA = new QEscritorio("qEscritorioA");
        final QPessoa qPessoaA = new QPessoa("qPessoaA");
        final QEscritorio qEscritorioB = new QEscritorio("qEscritorioB");
        final QPessoa qPessoaB = new QPessoa("qPessoaB");
        final QEscritorio qEscritorioC = new QEscritorio("qEscritorioC");
        final QPessoa qPessoaC = new QPessoa("qPessoaC");



        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qConfiguracaoCliente)
                .from(qConfiguracaoCliente)
                .leftJoin(qConfiguracaoCliente.escritorioPlanoEconomico, qEscritorioPlanoEconomico).fetchJoin()
                .leftJoin(qEscritorioPlanoEconomico.pessoa, qPessoaPlanoEconomico).fetchJoin()
                .leftJoin(qConfiguracaoCliente.escritorioA, qEscritorioA).fetchJoin()
                .leftJoin(qEscritorioA.pessoa, qPessoaA).fetchJoin()
                .leftJoin(qConfiguracaoCliente.escritorioB, qEscritorioB).fetchJoin()
                .leftJoin(qEscritorioB.pessoa, qPessoaB).fetchJoin()
                .leftJoin(qConfiguracaoCliente.escritorioC, qEscritorioC).fetchJoin()
                .leftJoin(qEscritorioA.pessoa, qPessoaC).fetchJoin()
                .where(qConfiguracaoCliente.id.eq(id)).fetchOne());
    }
}
