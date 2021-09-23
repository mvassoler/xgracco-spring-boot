package br.com.finchsolucoes.xgracco.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
public class ProcessoRelacionadoPK implements Serializable{

    private Long processo;
    private Long relacionado;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessoRelacionadoPK that = (ProcessoRelacionadoPK) o;
        return Objects.equals(processo, that.processo) &&
                Objects.equals(relacionado, that.relacionado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processo, relacionado);
    }
}
