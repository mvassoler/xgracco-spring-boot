package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDespesas;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusPagamentoDespesas;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;

import java.util.Calendar;
import java.util.List;

public interface ProcessoDespesasJpaRepository {
    List<ProcessoDespesas> findByPeriodoCliente(Calendar dataInicio, Calendar dataFim, Pessoa cliente, String sortProperty, Sorter.Direction sortDirection, Long page, Long limit);

    /**
     * Retorna uma lista de despesas por processo e número de documento.
     *
     * @param idProcessoUnico identificador único do processo.
     * @param numeroDocumento número do documento da despesa.
     * @return uma lista de {@link ProcessoDespesas}.
     */
    List<ProcessoDespesas> findByProcessoUnicoNumeroDocumento(String idProcessoUnico, String numeroDocumento);

    /**
     * Atualiza o status de pagamento de todos os {@link ProcessoDespesas} relacionados ao processo e número de documento fornecidos.
     *
     * @param processo                um objeto do tipo {@link Processo}.
     * @param numeroDocumento         uma string contendo o número do documento.
     * @param statusPagamentoDespesas status à ser atribuído à despesa.
     */
    void updateStatusPagamentoImportacao(Processo processo, String numeroDocumento, EnumStatusPagamentoDespesas statusPagamentoDespesas);

    /**
     * Encontra uma lista de {@link ProcessoDespesas} no cache.
     *
     * @return uma lista de {@link ProcessoDespesas}.
     */
    List<ProcessoDespesas> findAllCache();
}
