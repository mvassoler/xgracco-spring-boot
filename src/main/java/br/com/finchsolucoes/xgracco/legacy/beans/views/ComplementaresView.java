package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.entity.InformacoesAdicionais;

import java.util.List;

/**
 * Created by jordano on 03/06/16.
 */
public class ComplementaresView {

    private List<InformacoesAdicionais> informacoesAdicionais;

    public List<InformacoesAdicionais> getInformacoesAdicionais() {
        return informacoesAdicionais;
    }

    public void setInformacoesAdicionais(List<InformacoesAdicionais> informacoesAdicionais) {
        this.informacoesAdicionais = informacoesAdicionais;
    }
}
