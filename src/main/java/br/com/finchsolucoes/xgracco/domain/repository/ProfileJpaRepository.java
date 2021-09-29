package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.Profile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProfileJpaRepository {
    List<Profile> findByProcesso(Processo processo);

    List<Long> findIdByProfile(Long id);

    void create(Profile profile);

    void createProfiles(List<Profile> profiles);

    void update(Profile profile);

    void removeById(Long id);

    void removeByIds(Set<Long> ids);

    boolean validarNomeDiretorio(String nome, Long idProcesso, Long idProfile);

    Optional<Profile> findById(Long id);

    Optional<Profile> findProfileFinch(Long idProcesso);

    List<Profile> findProfilesByIdProcesso(Long idProfile);

    void remove(Profile profile);

    List<Profile> findArvoreProfilesByIdProcesso(Long idProcesso);

    Optional<Profile> findByProcessoAndNomeAndProfile(Processo processo, String nome, Profile pai);

    List<Profile> findListProcessoAndNome(Processo processo, String nome);

    Optional<Profile> findProfileRootByProcessoAndNome(Processo processo, String nome);

    Optional<Profile> findByNomeAndProcesso(Long idProcesso, String nome);
}
