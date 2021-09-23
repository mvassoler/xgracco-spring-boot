package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Relation(collectionRelation = "urgencias")
@Table(name = "URGENCIA", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao"}, name = "descUrgencia")})
@SequenceGenerator(allocationSize = 1, name = "seqUrgencia", sequenceName = "SEQ_URGENCIA")
@RelatorioInterface(titulo = "Urgencia")
@Audited
@Data
@Builder
public class Urgencia extends Entidade{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqUrgencia")
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO", length = 100, nullable = false)
    private String descricao;

    @QueryProjection
    public Urgencia(Long id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    public Urgencia(){}

    public Urgencia(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Urgencia that = (Urgencia) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
