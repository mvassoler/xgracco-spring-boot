package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author rodolpho.couto
 */
public class ConsultaSQLView implements Serializable {

    private String id;
    private String descricao;

    public ConsultaSQLView() {
    }

    public ConsultaSQLView(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ConsultaSQLView that = (ConsultaSQLView) o;
        return Objects.equals(this.getId(), that.getId()) &&
                Objects.equals(this.getDescricao(), that.getDescricao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getDescricao());
    }
}
