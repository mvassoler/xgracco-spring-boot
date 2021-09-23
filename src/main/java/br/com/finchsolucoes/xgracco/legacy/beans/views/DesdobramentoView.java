package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.ViewInterface;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author saraveronez
 */

public class DesdobramentoView implements Serializable {

    @ViewInterface(campo = "p.id", ordem = 0)
    private Long id;

    @ViewInterface(campo = "p.processoUnico", ordem = 1)
    private String idProcessoUnico;

    @ViewInterface(campo = "p.numero", ordem = 2)
    private String numero;

    @ViewInterface(campo = "p.acao.descricao", ordem = 3)
    private String acao;

    private List<DesdobramentoView> children;
    
    private String json;

    public DesdobramentoView(Long id, String idProcessoUnico, String numero, String acao) {
        this.id = id;
        this.idProcessoUnico = idProcessoUnico;
        this.numero = numero;
        this.acao = acao;
    }

    public DesdobramentoView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdProcessoUnico() {
        return idProcessoUnico;
    }

    public void setIdProcessoUnico(String idProcessoUnico) {
        this.idProcessoUnico = idProcessoUnico;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public List<DesdobramentoView> getChildren() {
        return children;
    }

    public void setChildren(List<DesdobramentoView> children) {
        this.children = children;
    }
    
     public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

}
