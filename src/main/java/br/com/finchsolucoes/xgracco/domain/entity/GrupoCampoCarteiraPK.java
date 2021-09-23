package br.com.finchsolucoes.xgracco.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jordano
 */
@Data
@Builder
public class GrupoCampoCarteiraPK implements Serializable {

    private Long carteira;

    private Long grupoCampo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrupoCampoCarteiraPK)) return false;

        GrupoCampoCarteiraPK that = (GrupoCampoCarteiraPK) o;

        if (getCarteira() != null ? !getCarteira().equals(that.getCarteira()) : that.getCarteira() != null)
            return false;
        return getGrupoCampo() != null ? getGrupoCampo().equals(that.getGrupoCampo()) : that.getGrupoCampo() == null;

    }

    @Override
    public int hashCode() {
        int result = getCarteira() != null ? getCarteira().hashCode() : 0;
        result = 31 * result + (getGrupoCampo() != null ? getGrupoCampo().hashCode() : 0);
        return result;
    }
}
