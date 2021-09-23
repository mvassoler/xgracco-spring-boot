package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Relation(collectionRelation = "tipos-logradouro")
@Table(name = "TIPOLOGRADOURO", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao"}, name = "TpLogDesc")})
@SequenceGenerator(allocationSize = 1, name = "seqTipoLogradouro", sequenceName = "SEQ_TIPOLOGRADOURO")
@Data
@Builder
@AllArgsConstructor
public class TipoLogradouro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqTipoLogradouro")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "DESCRICAO")
    private String descricao;

    public TipoLogradouro() {
    }

    public TipoLogradouro(Long id) {
        this.id = id;
    }

    public TipoLogradouro(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        TipoLogradouro that = (TipoLogradouro) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
