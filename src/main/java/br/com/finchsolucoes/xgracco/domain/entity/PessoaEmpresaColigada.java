package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.validation.Unique;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Marcelo Aguiar
 */
@Entity
@Relation(collectionRelation = "empresas-coligadas")
@Table(name = "PESSOA_EMPRESA_COLIGADA")
@SequenceGenerator(allocationSize = 1, name = "seqPessoaEmpresaColigada", sequenceName = "SEQ_PESSOA_EMPRESA_COLIGADA")
@Unique({"empresa", "pessoa"})
@Data
@Builder
@AllArgsConstructor
public class PessoaEmpresaColigada implements PessoaDependency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPessoaEmpresaColigada")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_EMPRESA")
    private Pessoa empresa;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @Column(name = "HOLDING")
    private Boolean holding;

    public PessoaEmpresaColigada() {
    }

    public PessoaEmpresaColigada(Long id) {
        this.id = id;
    }

    public PessoaEmpresaColigada(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        PessoaEmpresaColigada that = (PessoaEmpresaColigada) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
