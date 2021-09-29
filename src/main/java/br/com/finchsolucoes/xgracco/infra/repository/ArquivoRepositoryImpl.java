package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.exception.EntityNotFoundException;
import br.com.finchsolucoes.xgracco.domain.exception.IdNotNullException;
import br.com.finchsolucoes.xgracco.domain.exception.IdNullException;
import br.com.finchsolucoes.xgracco.domain.repository.ArquivoJpaRepository;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author Rodolpho Couto
 */
@Repository
public class ArquivoRepositoryImpl implements ArquivoJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Arquivo> findByProcesso(Processo processo) {
        final QArquivo qArquivo = QArquivo.arquivo;
        final QTipoDocumento qTipoDocumento = QTipoDocumento.tipoDocumento;
        final QProfile qProfile = QProfile.profile1;
        final QProcesso qProcesso = QProcesso.processo1;

        final List<Arquivo> arquivos = new JPAQueryFactory(entityManager)
                .select(QArquivo.create(
                        qArquivo.id,
                        qArquivo.nomeArquivo,
                        qArquivo.caminhoDocumento,
                        QTipoDocumento.create(
                                qTipoDocumento.id,
                                qTipoDocumento.descricao,
                                qTipoDocumento.padrao
                        ),
                        QProfile.create(
                                qProfile.id,
                                QProcesso.create(qProcesso.id)
                        )))
                .from(qArquivo)
                .join(qArquivo.tipoDocumento, qTipoDocumento)
                .join(qArquivo.profile, qProfile)
                .join(qProfile.processo, qProcesso)
                .where(qProfile.processo.eq(processo))
                .fetch();

        arquivos
                .stream()
                .filter(arquivo -> Objects.nonNull(arquivo.getProfile()))
                .filter(arquivo -> Objects.isNull(arquivo.getProfile().getId()))
                .forEach(arquivo -> arquivo.setProfile(null));

        return arquivos;
    }


    @Override
    public List<Arquivo> findByProcessoAndProfile(Processo processo, Profile profile) {
        final QArquivo qArquivo = QArquivo.arquivo;
        final QTipoDocumento qTipoDocumento = QTipoDocumento.tipoDocumento;
        final QProfile qProfile = QProfile.profile1;
        final QProcesso qProcesso = QProcesso.processo1;

        final List<Arquivo> arquivos = new JPAQueryFactory(entityManager)
                .select(QArquivo.create(
                        qArquivo.id,
                        qArquivo.nomeArquivo,
                        qArquivo.caminhoDocumento,
                        QTipoDocumento.create(
                                qTipoDocumento.id,
                                qTipoDocumento.descricao,
                                qTipoDocumento.padrao
                        ),
                        QProfile.create(
                                qProfile.id,
                                QProcesso.create(qProcesso.id)
                        )))
                .from(qArquivo)
                .join(qArquivo.tipoDocumento, qTipoDocumento)
                .join(qArquivo.profile, qProfile)
                .join(qProfile.processo, qProcesso)
                .where(qProfile.processo.eq(processo))
                .where(qProfile.eq(profile))
                .fetch();

        arquivos
                .stream()
                .filter(arquivo -> Objects.nonNull(arquivo.getProfile()))
                .filter(arquivo -> Objects.isNull(arquivo.getProfile().getId()))
                .forEach(arquivo -> arquivo.setProfile(null));

        return arquivos;
    }


    @Override
    public Optional<Arquivo> findByProcessoAndProfileAndId(Processo processo, Profile profile, Long id) {
        final QArquivo qArquivo = QArquivo.arquivo;
        final QTipoDocumento qTipoDocumento = QTipoDocumento.tipoDocumento;
        final QProfile qProfile = QProfile.profile1;
        final QProcesso qProcesso = QProcesso.processo1;

        final Optional<Arquivo> arquivoOptional = Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(QArquivo.create(
                        qArquivo.id,
                        qArquivo.nomeArquivo,
                        qArquivo.caminhoDocumento,
                        QTipoDocumento.create(
                                qTipoDocumento.id,
                                qTipoDocumento.descricao,
                                qTipoDocumento.padrao
                        ),
                        QProfile.create(
                                qProfile.id,
                                QProcesso.create(qProcesso.id)
                        )))
                .from(qArquivo)
                .join(qArquivo.tipoDocumento, qTipoDocumento)
                .join(qArquivo.profile, qProfile)
                .join(qProfile.processo, qProcesso)
                .where(qProfile.processo.eq(processo))
                .where(qProfile.eq(profile))
                .where(qArquivo.id.eq(id))
                .fetchOne());

        arquivoOptional
                .filter(arquivo -> Objects.nonNull(arquivo.getProfile()))
                .filter(arquivo -> Objects.isNull(arquivo.getProfile().getId()))
                .ifPresent(arquivo -> arquivo.setProfile(null));

        return arquivoOptional;
    }


    @Override
    public Optional<Arquivo> findByProfileAndNome(Profile profile, String nomeArquivo) {
        final QArquivo qArquivo = QArquivo.arquivo;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .selectFrom(qArquivo)
                .where(qArquivo.profile.eq(profile))
                .where(qArquivo.nomeArquivo.eq(nomeArquivo))
                .fetchOne());
    }


    @Override
    public Optional<Arquivo> findById(Long id) {
        final QArquivo qArquivo = QArquivo.arquivo;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qArquivo)
                .from(qArquivo)
                .where(qArquivo.id.eq(id))
                .fetchOne());
    }

    @Override
    public void create(Arquivo arquivo) {
        if (Objects.nonNull(arquivo.getId())) {
            throw new IdNotNullException();
        }

        entityManager.persist(arquivo);
        entityManager.flush();
    }

    @Override
    public void update(Arquivo arquivo) {
        Optional.ofNullable(entityManager.find(Arquivo.class, Optional.ofNullable(arquivo.getId()).orElseThrow(IdNullException::new))).orElseThrow(EntityNotFoundException::new);
        entityManager.merge(arquivo);
        entityManager.flush();
    }

    @Override
    public void removeById(Long id) {
        entityManager.remove(Optional.ofNullable(entityManager.find(Arquivo.class, id)).orElseThrow(EntityNotFoundException::new));
        entityManager.flush();
    }

    @Override
    public void removeByProfiles(Set<Long> profiles) {
        final QArquivo qArquivo = QArquivo.arquivo;

        new JPADeleteClause(entityManager, qArquivo)
                .where(qArquivo.profile.id.in(profiles))
                .execute();

        entityManager.flush();
    }

    @Override
    public boolean validarNomeDiretorio(String nome, Long idProfile) {
        final QArquivo qArquivo = QArquivo.arquivo;
        final QProfile qProfile = QProfile.profile1;

        final List<Arquivo> arquivos = new JPAQueryFactory(entityManager)
                .select(qArquivo)
                .from(qArquivo)
                .join(qArquivo.profile, qProfile)
                .where(qProfile.id.eq(idProfile))
                .where(qArquivo.nomeArquivo.eq(nome))
                .fetch();

        if (CollectionUtils.isEmpty(arquivos)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public String getCaminhoDocumentoByIdArquivo(Long idArquivo) {

        final QArquivo qArquivo = QArquivo.arquivo;

        return new JPAQueryFactory(entityManager)
                .select(qArquivo)
                .from(qArquivo)
                .where(qArquivo.id.eq(idArquivo)).fetchOne().getCaminhoDocumento();
    }


    @Override
    public Optional<Arquivo> findByIdArquivoFinch(Long idArquivoFinch) {

        final QArquivo qArquivo = QArquivo.arquivo;

        return Optional.ofNullable(new JPAQueryFactory(this.entityManager)
                .select(qArquivo)
                .from(qArquivo)
                .where(qArquivo.idArquivoFinch.eq(idArquivoFinch)).fetchOne());
    }

}
