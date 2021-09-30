package br.com.finchsolucoes.xgracco.legacy.beans.parametros;

import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.CampoParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;

/**
 *
 * @author Marcelo Aguiar
 */
public class ParametrosDashboardCoordenadorOperacional implements Identificavel<Long> {
    
    private Long id;
    
    @CampoParametro(campo = EnumParametro.DASHBOARDCOORDOPER_INTERVALO_FILTRO)
    private Long intervaloFiltroData;
    
    @CampoParametro(campo = EnumParametro.DASHBOARDCOORDOPER_QTDE_NOMES)
    private Long qtdeNomesGraficoProdutividade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIntervaloFiltroData() {
        return intervaloFiltroData;
    }

    public void setIntervaloFiltroData(Long intervaloFiltroData) {
        this.intervaloFiltroData = intervaloFiltroData;
    }

    public Long getQtdeNomesGraficoProdutividade() {
        return qtdeNomesGraficoProdutividade;
    }

    public void setQtdeNomesGraficoProdutividade(Long qtdeNomesGraficoProdutividade) {
        this.qtdeNomesGraficoProdutividade = qtdeNomesGraficoProdutividade;
    }

    public Long getPK() {
        return id;
    }

    public String getTextoLog() {
        return "";
    }
}
