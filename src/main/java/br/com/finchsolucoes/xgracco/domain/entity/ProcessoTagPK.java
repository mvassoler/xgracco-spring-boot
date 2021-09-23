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
public class ProcessoTagPK implements Serializable{

    private Long processo;
    private Long tag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessoTagPK that = (ProcessoTagPK) o;
        return Objects.equals(processo, that.processo) &&
                Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processo, tag);
    }
}
