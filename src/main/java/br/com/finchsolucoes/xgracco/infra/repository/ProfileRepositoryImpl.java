package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.core.handler.exception.EntityNotFoundException;
import br.com.finchsolucoes.xgracco.core.handler.exception.IdNotNullException;
import br.com.finchsolucoes.xgracco.core.handler.exception.IdNullException;
import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.repository.ProfileJpaRepository;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author Rodolpho Couto
 */
@Repository
public class ProfileRepositoryImpl extends AbstractJpaRepository<Profile,Long> implements ProfileJpaRepository {

    //TODO - ACERTAR ESTA CLASSE

    @Override
    public List<Profile> findByProcesso(Processo processo) {
        final QProfile qProfile = QProfile.profile1;
        final QProcesso qProcesso = QProcesso.processo1;
        final QProfile qProfilePai = new QProfile("profilePai");
        final QPessoa qPessoa = QPessoa.pessoa;
        final QUsuario qUsuario = QUsuario.usuario;

        final List<Profile> profiles = new JPAQueryFactory(entityManager)
                .select(QProfile.create(
                        qProfile.id,
                        qProfile.nomeProfile,
                        qProfile.dataCriacao,
                        QProcesso.create(qProcesso.id),
                        QProfile.create(qProfilePai.id),
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nomeRazaoSocial,
                                qPessoa.apelidoFantasia,
                                qPessoa.rgInscricaoEstadual,
                                qPessoa.cpfCnpj,
                                qPessoa.tipo,
                                qPessoa.ativo,
                                QUsuario.create(
                                        qUsuario.id,
                                        qUsuario.login,
                                        qPessoa
                                )
                        )
                ))
                .from(qProfile)
                .join(qProfile.processo, qProcesso)
                .leftJoin(qProfile.profile, qProfilePai)
                .leftJoin(qProfile.autor, qPessoa)
                .leftJoin(qPessoa.usuarioSistema, qUsuario)
                .where(qProcesso.eq(processo))
                .fetch();

        profiles
                .stream()
                .filter(profile -> Objects.nonNull(profile.getProfile()))
                .filter(profile -> Objects.isNull(profile.getProfile().getId()))
                .forEach(profile -> profile.setProfile(null));

        profiles
                .stream()
                .filter(profile -> Objects.nonNull(profile.getAutor()))
                .filter(profile -> Objects.isNull(profile.getAutor().getId()))
                .forEach(profile -> profile.setAutor(null));

        return profiles;
    }

    @Override
    public List<Long> findIdByProfile(Long id) {
        final QProfile qProfile = QProfile.profile1;

        return new JPAQueryFactory(entityManager)
                .select(qProfile.id)
                .from(qProfile)
                .where(qProfile.profile.id.eq(id))
                .fetch();
    }

    @Override
    public void create(Profile profile) {
        if (Objects.nonNull(profile.getId())) {
            throw new IdNotNullException();
        }

        entityManager.persist(profile);
        entityManager.flush();
    }

    @Override
    public void createProfiles(List<Profile> profiles) {

        for (Profile profile : profiles) {
            if (Objects.nonNull(profile.getId())) {
                throw new IdNotNullException();
            }
            entityManager.persist(profile);
        }

        entityManager.flush();

    }

    @Override
    public void update(Profile profile) {
        Optional.ofNullable(entityManager.find(Profile.class, Optional.ofNullable(profile.getId()).orElseThrow(IdNullException::new))).orElseThrow(EntityNotFoundException::new);
        entityManager.merge(profile);
        entityManager.flush();
    }

    @Override
    public void remove(Profile profile) {
        entityManager.remove(profile);
    }

    @Override
    public void removeById(Long id) {
        entityManager.remove(Optional.ofNullable(entityManager.find(Profile.class, id)).orElseThrow(EntityNotFoundException::new));
        entityManager.flush();
    }

    @Override
    public void removeByIds(Set<Long> ids) {
        final QProfile qProfile = QProfile.profile1;

        new JPADeleteClause(entityManager, qProfile)
                .where(qProfile.id.in(ids))
                .execute();

        entityManager.flush();
    }

    @Override
    public boolean validarNomeDiretorio(String nome, Long idProcesso, Long idProfile) {
        QProfile qProfile = QProfile.profile1;
        QProcesso qProcesso = QProcesso.processo1;

        final JPAQuery<Profile> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qProfile)
                .from(qProfile)
                .join(qProfile.processo, qProcesso)
                .where(qProfile.nomeProfile.equalsIgnoreCase(nome))
                .where(qProcesso.id.eq(idProcesso));

        if (Objects.nonNull(idProfile)) {
            jpaQuery.where(qProfile.profile.id.eq(idProfile));
        }

        final List<Profile> diretorios = jpaQuery.fetch();

        if (CollectionUtils.isEmpty(diretorios)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Profile> findByNomeAndProcesso(Long idProcesso, String nome) {
        QProfile qProfile = QProfile.profile1;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qProfile)
                .from(qProfile)
                .where(qProfile.processo.id.eq(idProcesso))
                .where(qProfile.nomeProfile.likeIgnoreCase(nome))
                .where(qProfile.isFinch.eq(Boolean.TRUE))
                .fetchOne());
    }

    @Override
    public Optional<Profile> findProfileFinch(Long idProcesso) {
        QProfile qProfile = QProfile.profile1;
        QProfile qProfilePai = new QProfile("profilePai");
        QArquivo qArquivo = QArquivo.arquivo;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qProfile)
                .from(qProfile)
                /*.leftJoin(qProfile.arquivos, qArquivo).fetchJoin()
                .leftJoin(qProfile.profiles, qProfilePai)*/
                .where(qProfile.processo.id.eq(idProcesso))
                .where(qProfile.isFinch.eq(true))
                .fetchOne());
    }

    @Override
    public Optional<Profile> findById(Long id) {
        QProfile qProfile = QProfile.profile1;
        QProfile qProfilePai = new QProfile("profilePai");
        QArquivo qArquivo = QArquivo.arquivo;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qProfile)
                .from(qProfile)
                /*.leftJoin(qProfile.arquivos, qArquivo).fetchJoin()
                .leftJoin(qProfile.profiles, qProfilePai)*/
                .where(qProfile.id.eq(id))
                .fetchOne());
    }

    @Override
    @Transactional
    public List<Profile> findProfilesByIdProcesso(Long idProfile) {

        QProfile qProfile = QProfile.profile1;

        return new JPAQueryFactory(entityManager)
                .selectDistinct(qProfile)
                .from(qProfile)
                .where(qProfile.processo.id.eq(idProfile))
                .fetch();

    }

    @Override
    public List<Profile> findArvoreProfilesByIdProcesso(Long idProfile) {

        QProfile qProfile = QProfile.profile1;
        QArquivo qArquivo = QArquivo.arquivo;

        return new JPAQueryFactory(entityManager)
                .select(qProfile)
                .from(qProfile)
                /*.leftJoin(qProfile.arquivos, qArquivo).fetchJoin()*/
                .where(qProfile.processo.id.eq(idProfile))
                .where(qProfile.profile.isNull())
                .fetch();

    }


    @Override
    public Optional<Profile> findByProcessoAndNomeAndProfile(Processo processo, String nome, Profile pai) {
        QProfile qProfile = QProfile.profile1;
        QProcesso qProcesso = QProcesso.processo1;

        final JPAQuery<Profile> query = new JPAQueryFactory(entityManager)
                .select(qProfile)
                .from(qProfile)
                .join(qProfile.processo, qProcesso)
                .where(qProcesso.eq(processo))
                .where(qProfile.nomeProfile.eq(nome));

        if (Objects.nonNull(pai)) {
            query.where(qProfile.profile.eq(pai));
        }

        return Optional.ofNullable(query.fetchOne());
    }

    @Override
    public List<Profile> findListProcessoAndNome(Processo processo, String nome) {
        QProfile qProfile = QProfile.profile1;
        QProcesso qProcesso = QProcesso.processo1;

        return new JPAQueryFactory(entityManager)
                .select(qProfile)
                .from(qProfile)
                .join(qProfile.processo, qProcesso)
                .where(qProcesso.eq(processo))
                .where(qProfile.nomeProfile.eq(nome))
                .fetch();
    }

    public Optional<Profile> findProfileRootByProcessoAndNome(Processo processo, String nome) {
        QProfile qProfile = QProfile.profile1;
        QProcesso qProcesso = QProcesso.processo1;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qProfile)
                .from(qProfile)
                .join(qProfile.processo, qProcesso)
                .where(qProfile.profile.isNull())
                .where(qProfile.nomeProfile.eq(nome))
                .where(qProcesso.eq(processo))
                .fetchOne());

    }
}
