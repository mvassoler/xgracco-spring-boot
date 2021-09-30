package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Andre Farina
 */
@Entity
@Relation(collectionRelation = "cidades")
@Table(name = "CIDADE")
@SequenceGenerator(allocationSize = 1, name = "seqCidade", sequenceName = "SEQ_CIDADE")
@Data
@Builder
@AllArgsConstructor
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqCidade")
    @Column(name = "ID")
    private Long id;

    //@NotBlank
    //@Size(max = 100)
    @Column(name = "DESCRICAO")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    //@NotNull
    @JoinColumn(name = "UF_ID")
    private Uf uf;

    public Cidade() {
    }

    public Cidade(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Cidade(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Cidade cidade = (Cidade) o;
        return Objects.equals(this.getId(), cidade.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
