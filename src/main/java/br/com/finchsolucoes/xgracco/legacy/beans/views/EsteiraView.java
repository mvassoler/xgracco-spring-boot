package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.ViewInterface;

import java.io.Serializable;

public class EsteiraView implements Serializable {

    @ViewInterface(campo = "e.id", ordem = 0)
    private Long id;
    
    @ViewInterface(campo = "e.descricao", ordem = 1)
    private String descricao;
    
    public EsteiraView(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
