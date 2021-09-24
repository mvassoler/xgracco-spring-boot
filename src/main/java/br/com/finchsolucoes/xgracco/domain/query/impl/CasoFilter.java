package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Caso;
import br.com.finchsolucoes.xgracco.domain.entity.CasoProcesso;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class CasoFilter implements Filter<Caso> {

    private String descricao;
    private String identificador;
    private Usuario responsavel;
    private Processo processo;
    private CasoProcesso processoPai;

    public CasoFilter(String descricao, String identificador, Usuario responsavel) {
        this.descricao = descricao;
        this.identificador = identificador;
        this.responsavel = responsavel;
    }

    public String getDescricao() {
        return descricao;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public Processo getProcesso() {
        return processo;
    }

    public CasoProcesso getProcessoPai() {
        return processoPai;
    }

    public String getIdentificador() {
        return identificador;
    }
}
