package br.com.finchsolucoes.xgracco.domain.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(ProcessoTagPK.class)
@Relation(collectionRelation = "processos-tags")
@Table(name = "PROCESSO_TAG")
@Data
@Builder
public class ProcessoTag implements Serializable{

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO", referencedColumnName = "ID")
    private Processo processo;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TAG", referencedColumnName = "ID")
    private Tag tag;

    @QueryProjection
    public ProcessoTag(Processo processo, Tag tag) {
        this.processo = processo;
        this.tag = tag;
    }

    public ProcessoTag(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessoTag that = (ProcessoTag) o;

        if (processo != null ? !processo.equals(that.processo) : that.processo != null) return false;
        return tag != null ? tag.equals(that.tag) : that.tag == null;
    }

    @Override
    public int hashCode() {
        int result = processo != null ? processo.hashCode() : 0;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProcessoTag{" +
                "processo=" + processo +
                ", tag=" + tag +
                '}';
    }
}
