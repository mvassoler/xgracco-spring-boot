package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.entity.Esteira;
import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPapel;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface PessoaJpaRepository {
    List<Pessoa> findByEsteira(Esteira esteira, String nomeRazaoSocial);

    Optional<Pessoa> findByIdFetchCarteiras(Long id);

    Optional<Pessoa> findByNomeAndCarteira(String nome, Carteira carteira);

    List<Pessoa> findByNome(String nome);

    List<Pessoa> find(Query<Pessoa> query);

    List<Pessoa> findAllCache();

    List<Pessoa> findByNomeAndTipoPapelAndSistema(String nome, EnumTipoPapel tipoPapel, boolean sistema);

    long count(Query<Pessoa> query);

    Optional<Pessoa> findById(Long id);

    Long countByIdentidadePessoa(Pessoa pessoa, boolean update);

    Pessoa findByNomeRazaoSocialAndCpfCnpj(String nomeRazaoSocial, String cpfCnpj);

    List<Pessoa> findByNomeRazaoSocial(String nomeRazaoSocial);

    Optional<Pessoa> findByCpfCnpj(String cpfCnpj);

    Optional<Pessoa> findPessoaUsuarioByNomeRazaoSocial(String nomeRazaoSocial);
}
