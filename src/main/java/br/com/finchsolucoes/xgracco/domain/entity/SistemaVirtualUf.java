package br.com.finchsolucoes.xgracco.domain.entity;


import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;

@Entity
@IdClass(SistemaVirtualUfPK.class)
@Relation(collectionRelation = "sistemas-virtuais-ufs")
@Table(name = "SISTEMAVIRTUAL_UF")
@Data
@Builder
public class SistemaVirtualUf {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SISTEMAVIRTUAL_ID", referencedColumnName = "ID", insertable = false)
    private SistemaVirtual sistemaVirtual;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UF_ID", referencedColumnName = "ID", insertable = false)
    private Uf uf;

    public SistemaVirtualUf(){}

    public SistemaVirtualUf(SistemaVirtual sistemaVirtual, Uf uf) {
        this.sistemaVirtual = sistemaVirtual;
        this.uf = uf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SistemaVirtualUf that = (SistemaVirtualUf) o;

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
