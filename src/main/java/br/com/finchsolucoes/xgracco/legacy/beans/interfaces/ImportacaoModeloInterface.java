package br.com.finchsolucoes.xgracco.legacy.beans.interfaces;

import br.com.finchsolucoes.xgracco.core.eventlistener.events.ImportacaoPlanilhaEvent;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;
import java.util.Map;

/**
 * Interface para padronizar a importação dos modelos de processo
 *
 * @author Marcelo Aguiar
 * @since 2.1
 */
public interface ImportacaoModeloInterface {

    /**
     * Processa a planilha de acordo com a lógica de cada rotina de importação
     *
     * @param planilha
     * @return
     * @throws Exception
     */
    HSSFWorkbook processarModelo(HSSFWorkbook planilha);

    /**
     * Retorna as inconsistências encontradas durante a validação
     *
     * @return
     */
    Map<Cell, String> getInconsistencias();

    /**
     * Retorna as informações para serem preenchidas em uma aba de resultado
     *
     * @return
     */
    List<String> getLinhasFeedbackImportacao();

    /**
     * Registra o evento de inconsistências
     *
     * @return
     */
    void addEventListener(ImportacaoPlanilhaEvent evento);
}
