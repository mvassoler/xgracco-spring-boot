package br.com.finchsolucoes.xgracco.legacy.beans.views;

/**
 *
 * @author marceloaguiar
 */
public class ObjetoListaValoresRelatorio {
    private Object value;
    private String label;

    public ObjetoListaValoresRelatorio() {
    }

    public ObjetoListaValoresRelatorio(Object value, String label) {
        this.value = value;
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    
}
