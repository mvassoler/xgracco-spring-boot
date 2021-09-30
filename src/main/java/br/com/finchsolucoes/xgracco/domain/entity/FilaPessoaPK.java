package br.com.finchsolucoes.xgracco.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author guicamargo
 */
@Data
@Builder
public class FilaPessoaPK implements Serializable{

    private Long fila;
    private Long pessoa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilaPessoaPK that = (FilaPessoaPK) o;

        if (fila != null ? !fila.equals(that.fila) : that.fila != null) return false;
        return pessoa != null ? pessoa.equals(that.pessoa) : that.pessoa == null;
    }

    @Override
    public int hashCode() {
        int result = fila != null ? fila.hashCode() : 0;
        result = 31 * result + (pessoa != null ? pessoa.hashCode() : 0);
        return result;
    }
}
