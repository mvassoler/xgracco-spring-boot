package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.IndiceEconomico;
import br.com.finchsolucoes.xgracco.domain.enums.EnumPeriodicidadeJuros;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoIndiceEconomico;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.util.Set;

/**
 * Classe filtro para IndiceEconomico
 *
 * @author Paulo Marçon
 * @since 2.1
 */
public class IndiceEconomicoFilter implements Filter<IndiceEconomico> {

    private String descricao;
    private Set<EnumTipoIndiceEconomico> tipoIndiceEconomico;
    private EnumPeriodicidadeJuros periodoJurosCorrecao;

    public IndiceEconomicoFilter(String descricao, Set<EnumTipoIndiceEconomico> tipoIndiceEconomico, EnumPeriodicidadeJuros periodoJurosCorrecao) {
        this.descricao = descricao;
        this.tipoIndiceEconomico = tipoIndiceEconomico;
        this.periodoJurosCorrecao = periodoJurosCorrecao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<EnumTipoIndiceEconomico> getTipoIndiceEconomico() {
        return tipoIndiceEconomico;
    }

    public void setTipoIndiceEconomico(Set<EnumTipoIndiceEconomico> tipoIndiceEconomico) {
        this.tipoIndiceEconomico = tipoIndiceEconomico;
    }

    public EnumPeriodicidadeJuros getPeriodoJurosCorrecao() {
        return periodoJurosCorrecao;
    }

    public void setPeriodoJurosCorrecao(EnumPeriodicidadeJuros periodoJurosCorrecao) {
        this.periodoJurosCorrecao = periodoJurosCorrecao;
    }
}
