package br.com.finchsolucoes.xgracco.legacy.beans.views;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author enedycordeiro
 */
public class HistoricVariableInstanceView {

    public static final String NOME_VARIAVEL_HISTORICO_TAREFAS = "Tarefas agendadas";
    private String id;
    private String name;
    private Object value;
    private int tipoCampo;
    private String caminhoAnexo;

    public HistoricVariableInstanceView() {
    }

    public HistoricVariableInstanceView(String name, Object value, int tipoCampo) {
        this.name = name;
        this.value = value;
        this.tipoCampo = tipoCampo;
    }

    public HistoricVariableInstanceView(String name, Object value, int tipoCampo, String caminhoAnexo) {
        this(name, value, tipoCampo);
        this.caminhoAnexo = caminhoAnexo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getTipoCampo() {
        return tipoCampo;
    }

    public void setTipoCampo(int tipoCampo) {
        this.tipoCampo = tipoCampo;
    }

    public String getCaminhoAnexo() {
        return caminhoAnexo;
    }

    public void setCaminhoAnexo(String caminhoAnexo) {
        this.caminhoAnexo = caminhoAnexo;
    }

    public String getCaminhoAnexoEncoded() {
        if (caminhoAnexo == null || !caminhoAnexo.contains("/")) {
            return null;
        }

        try {
            return caminhoAnexo.substring(0, caminhoAnexo.lastIndexOf("/") + 1) + URLEncoder.encode(caminhoAnexo.substring(caminhoAnexo.lastIndexOf("/") + 1), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return caminhoAnexo;
        }
    }
}
