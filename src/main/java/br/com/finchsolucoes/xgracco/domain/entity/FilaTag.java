package br.com.finchsolucoes.xgracco.domain.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author guicamargo
 */
@Entity
@IdClass(FilaTagPK.class)
@Relation(collectionRelation = "filas-tags")
@Table(name = "FILA_TAG")
@Data
@Builder
public class FilaTag implements Serializable{

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FILA", referencedColumnName = "ID")
    private Fila fila;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TAG", referencedColumnName = "ID")
    private Tag tag;

    @QueryProjection
    public FilaTag(Fila fila, Tag tag) {
        this.fila = fila;
        this.tag = tag;
    }

    public FilaTag() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilaTag filaTag = (FilaTag) o;

        if (fila != null ? !fila.equals(filaTag.fila) : filaTag.fila != null) return false;
        return tag != null ? tag.equals(filaTag.tag) : filaTag.tag == null;
    }

    @Override
    public int hashCode() {
        int result = fila != null ? fila.hashCode() : 0;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FilaTag{" +
                "fila=" + fila +
                ", tag=" + tag +
                '}';
    }
}