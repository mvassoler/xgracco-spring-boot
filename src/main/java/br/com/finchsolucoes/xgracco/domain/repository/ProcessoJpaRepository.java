package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.Tag;
import br.com.finchsolucoes.xgracco.domain.enums.EnumOrigemProcesso;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProcessoJpaRepository {
    Optional<Processo> findByNumero(String numero);

    List<Processo> findByParteContraria(String nome);

    List<Processo> findBySistemaVirtual(Long idSistemaVirtual);

    Long findByNumeroAndCarteira(Processo processo, Boolean processoNovo);

    List<Processo> findProcessoByEscritorio(Long idEscritorio);

    Optional<Processo> findByProcessoUnicoGracco(String processoUnicoGracco);

    Optional<Processo> findByProcessoUnico(String processoUnico);

    Long countProcessosSemAtividadePorCarteira(List<Long> carteiras, List<Long> idEscritorio);

    List<Processo> findProcessosArquivos();

    List<Processo> findProcessosNaoPrecificados();

    Long updateValorProvisao(Long id, BigDecimal valorProvisao, BigDecimal valorPrecificacao);

    List<Processo> findAllCache();

    Long countProcessosAtivosByEscritorio(Long idEscritorio, EnumOrigemProcesso origemProcesso);

    List<Processo> findProcessosSincronismo();

    List<Processo> findProcessosDuplicadosIntegracao();

    List<Processo> findProcessosJoinPedidos();

    List<Processo> findbyControleCliente(String controleCliente);

    Optional<Processo> findByNumeroAndCarteira(String numero, Carteira carteira);

    List<Processo> findByProcessoPrincipal(Processo processoPrincipal);

    Optional<Processo> findOnlyProcessoByProcessoUnico(String processoUnico);

    List<Processo> findByTag(Tag tag);

    Long countFindByTag(Tag tag);
}
