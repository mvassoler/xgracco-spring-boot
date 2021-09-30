package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.ViewInterface;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author saraveronez
 */
public class TreeRootView extends TreeDataView implements Serializable {

    @ViewInterface(campo = "a.nomeProfile", ordem = 1)
    private String text;

    @ViewInterface(campo = "a.dataCriacao", ordem = 2)
    private Calendar dataCriacao;

    private String state = "closed";

    @JsonProperty("children")
    private List<TreeDataView> children = new ArrayList();

    @QueryProjection
    public TreeRootView(Long id, String text, Calendar dataCriacao) {
        super(id);
        this.text = text;
        this.setDataCriacao(dataCriacao);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDataCriacao(Calendar dataCriacao) {
        this.getAttributes().put("dataCriacao", dataCriacao);
        this.dataCriacao = dataCriacao;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<TreeDataView> addAllChildren(List<TreeDataView> children) {
        this.children.addAll(children);
        return this.children;
    }

    public List<TreeDataView> getChildren() {
        return children;
    }

    public void setChildren(List<TreeDataView> children) {
        this.children = children;
    }

}
