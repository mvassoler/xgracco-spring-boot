package br.com.finchsolucoes.xgracco.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author guicamargo
 */
@Data
@Builder
public class FilaTagPK implements Serializable{

    private Long fila;
    private Long tag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilaTagPK filaTagPK = (FilaTagPK) o;
        return Objects.equals(fila, filaTagPK.fila) &&
                Objects.equals(tag, filaTagPK.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fila, tag);
    }
}
