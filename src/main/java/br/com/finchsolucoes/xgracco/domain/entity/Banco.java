package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Marcelo Aguiar
 */
@Entity
@Relation(collectionRelation = "bancos")
@Table(name = "BANCO", uniqueConstraints = {@UniqueConstraint(columnNames = {"codigo", "descricao"}, name = "banco")})
@SequenceGenerator(allocationSize = 1, name = "seqBanco", sequenceName = "SEQ_BANCO")
@Data
@Builder
@AllArgsConstructor
public class Banco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqBanco")
    @Column(name = "ID")
    private Long id;

    //@NotBlank
    //@Size(max = 30)
    @Column(name = "CODIGO")
    private String codigo;

    //@NotBlank
    //@Size(max = 255)
    @Column(name = "DESCRICAO")
    private String descricao;

    //@NotBlank
    //@Size(max = 255)
    @Column(name = "SITE")
    private String site;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "banco")
    private List<PessoaDadoBancario> dadosBancarios;

    public Banco() {
    }

    public Banco(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Banco(Long id, String codigo, String descricao, String site) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Banco banco = (Banco) o;
        return Objects.equals(this.getId(), banco.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
