package br.com.finchsolucoes.xgracco.domain.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(ProcessoRelacionadoPK.class)
@Relation(collectionRelation = "processos-relacionados")
@Table(name = "PROCESSO_RELACIONADO")
@Data
@Builder
public class ProcessoRelacionado implements Serializable{

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO", referencedColumnName = "ID")
    private Processo processo;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO_RELACIONADO", referencedColumnName = "ID")
    private Processo relacionado;

    @QueryProjection
    public ProcessoRelacionado(Processo processo, Processo relacionado) {
        this.processo = processo;
        this.relacionado = relacionado;
    }

    public ProcessoRelacionado(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessoRelacionado that = (ProcessoRelacionado) o;

        if (processo != null ? !processo.equals(that.processo) : that.processo != null) return false;
        return relacionado != null ? relacionado.equals(that.relacionado) : that.relacionado == null;
    }

    @Override
    public int hashCode() {
        int result = processo != null ? processo.hashCode() : 0;
        result = 31 * result + (relacionado != null ? relacionado.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProcessoRelacionado{" +
                "processo=" + processo +
                ", relacionado=" + relacionado +
                '}';
    }
}
