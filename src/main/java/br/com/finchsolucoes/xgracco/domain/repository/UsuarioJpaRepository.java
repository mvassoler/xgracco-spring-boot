package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UsuarioJpaRepository {

    List<Usuario> findAll();


    List<Usuario> findByCarteira(Carteira carteira);


    Optional<Usuario> findByLogin(String login);


    Optional<Usuario> findByLoginExterno(String login);


    void updateSenha(Usuario usuario, String novaSenha);

    Optional<Usuario> findUsuarioProcessoByUsuario(Usuario usuario);

    List<Usuario> findByEscritorios(Set<Escritorio> escritorios);


    List<Agenda> findAgendaByUsuario(Pessoa pessoa);


    List<SolicitacaoLog> findSolicitacoesByUsuario(Pessoa pessoa);


    List<Processo> findProcessosOperacionalByUsuario(Usuario usuario);

    List<CarteiraEvento> findEventosCarteiraByUsuario(Usuario usuario);

    List<ModeloAgendamento> findModelosAgendamentoByUsuario(Pessoa pessoa);

    List<TarefaStatusFinalAgendamento> findTarefaStatusFinalAgendamentoByUsuario(Usuario usuario);


    List<Esteira> findEsteiraByUsuario(Pessoa pessoa);


    List<Fila> findFilaByUsuario(Pessoa pessoa);

    List<Usuario> findAllCache();


    Optional<Usuario> findByIdFetchOnlyPessoa(Long id);


    Optional<Usuario> findLoginSenhaById(Long id);


    Optional<List<Usuario>> findByEmail(String email);


    List<Usuario> findUsuariosLogados(Query<Usuario> query);

    Long findUsuariosLogadosCount(Query<Usuario> query);


    List<Usuario> find(Query<Usuario> query);


    long count(Query<Usuario> query);




}
