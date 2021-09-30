package br.com.finchsolucoes.xgracco.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SistemaVirtualUfPK implements Serializable{

    private Long sistemaVirtual;

    private Long uf;

    public SistemaVirtualUfPK() {   }

    public SistemaVirtualUfPK(Long sistemaVirtual, Long uf) {
        this.sistemaVirtual = sistemaVirtual;
        this.uf = uf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SistemaVirtualUfPK that = (SistemaVirtualUfPK) o;

        if (sistemaVirtual != null ? !sistemaVirtual.equals(that.sistemaVirtual) : that.sistemaVirtual != null)
            return false;
        return uf != null ? uf.equals(that.uf) : that.uf == null;
    }

    @Override
    public int hashCode() {
        int result = sistemaVirtual != null ? sistemaVirtual.hashCode() : 0;
        result = 31 * result + (uf != null ? uf.hashCode() : 0);
        return result;
    }
}
