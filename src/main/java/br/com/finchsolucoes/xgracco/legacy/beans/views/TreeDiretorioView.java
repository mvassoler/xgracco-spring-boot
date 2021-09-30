/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.ViewInterface;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author saraveronez
 */
public class TreeDiretorioView extends TreeDataView implements Serializable {

    @ViewInterface(campo = "a.descricao", ordem = 1)
    private String text;

    @ViewInterface(campo = "a.isFinch", ordem = 2)
    private Boolean isFinch;
    
    private String state = "closed";

    @JsonProperty("children")
    private List<TreeDataView> children = new ArrayList();

    public TreeDiretorioView(Long id, String text, Boolean isFinch) {
        super(id);
        this.text = text;
        this.isFinch = isFinch;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getIsFinch() {
        return isFinch;
    }

    public void setIsFinch(Boolean isFinch) {
        this.isFinch = isFinch;
    }

}
