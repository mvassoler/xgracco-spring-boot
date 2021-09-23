/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.ViewInterface;
import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author saraveronez
 */
public class TreeChildView extends TreeDataView implements Serializable {

    @ViewInterface(campo = "a.nomeArquivo", ordem = 1)
    private String text;

    @ViewInterface(campo = "a.tipoDocumento.descricao", ordem = 2)
    private String descricaoTipoDocumento;

    @ViewInterface(campo = "a.dataAnexo", ordem = 3)
    private Calendar dataAnexo;

    @ViewInterface(campo = "a.idSistemaOrigem", ordem = 4)
    private int idSistemaOrigem;

    @ViewInterface(campo = "a.caminhoDocumento", ordem = 5)
    private String caminhoDocumento;

    @QueryProjection
    public TreeChildView(Long id, String text, String descricaoTipoDocumento, Calendar dataAnexo, int idSistemaOrigem, String caminhoDocumento) {
        super(id);
        this.text = text;
        this.setDescricaoTipoDocumento(descricaoTipoDocumento);
        this.setDataAnexo(dataAnexo);
        this.setIdSistemaOrigem(idSistemaOrigem);
        this.setCaminhoDocumento(caminhoDocumento);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDescricaoTipoDocumento(String descricaoTipoDocumento) {
        this.getAttributes().put("descricaoTipoDocumento", descricaoTipoDocumento);
        this.descricaoTipoDocumento = descricaoTipoDocumento;
    }

    public void setDataAnexo(Calendar dataAnexo) {
        this.getAttributes().put("dataAnexo", dataAnexo);
        this.dataAnexo = dataAnexo;
    }

    public void setIdSistemaOrigem(int idSistemaOrigem) {
        this.getAttributes().put("idSistemaOrigem", idSistemaOrigem);
        this.idSistemaOrigem = idSistemaOrigem;
    }

    public void setCaminhoDocumento(String caminhoDocumento) {
        this.getAttributes().put("caminhoDocumento", caminhoDocumento);
        this.caminhoDocumento = caminhoDocumento;
    }
}
