package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.validation.Unique;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author Marcelo Aguiar
 */
@Entity
@Table(name = "PESSOA_DADO_BANCARIO")
@Relation(collectionRelation = "dados-bancarios")
@SequenceGenerator(allocationSize = 1, name = "seqPessoaDadoBancario", sequenceName = "SEQ_PESSOA_DADO_BANCARIO")
@Unique({"banco", "agencia", "conta", "pessoa"})
@Data
@Builder
@AllArgsConstructor
public class PessoaDadoBancario extends Entidade implements PessoaDependency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPessoaDadoBancario")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANCO_FK")
    private Banco banco;

    @NotBlank
    @Size(max = 255)
    @Column(name = "DESCRICAO")
    private String descricao;

    @NotBlank
    @Size(max = 50)
    @Column(name = "AGENCIA")
    private String agencia;

    @NotBlank
    @Size(max = 50)
    @Column(name = "CONTA")
    private String conta;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    public PessoaDadoBancario() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        PessoaDadoBancario that = (PessoaDadoBancario) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
