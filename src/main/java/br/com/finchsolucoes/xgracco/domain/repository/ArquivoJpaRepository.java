package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Arquivo;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.Profile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ArquivoJpaRepository {
    List<Arquivo> findByProcesso(Processo processo);

    List<Arquivo> findByProcessoAndProfile(Processo processo, Profile profile);

    Optional<Arquivo> findByProcessoAndProfileAndId(Processo processo, Profile profile, Long id);

    Optional<Arquivo> findByProfileAndNome(Profile profile, String nomeArquivo);

    Optional<Arquivo> findById(Long id);

    void create(Arquivo arquivo);

    void update(Arquivo arquivo);

    void removeById(Long id);

    void removeByProfiles(Set<Long> profiles);

    boolean validarNomeDiretorio(String nome, Long idProfile);

    String getCaminhoDocumentoByIdArquivo(Long idArquivo);

    Optional<Arquivo> findByIdArquivoFinch(Long idArquivoFinch);
}
