package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.core.validation.Unique;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Jordano
 */
@Entity
@Table(name = "PESSOA_EMPRESA_COLABORADOR")
@Relation(collectionRelation = "empresas-colaboradores")
@SequenceGenerator(allocationSize = 1, name = "seqPessoaEmpresaColaborador", sequenceName = "SEQ_PESSOA_EMPRESA_COLABORADOR")
@Unique({"dataEntrada", "dataSaida", "pessoa", "empresa"})
@Data
@Builder
@AllArgsConstructor
public class PessoaEmpresaColaborador implements PessoaDependency {

    private static final long serialVersionUID = -1939097167825052977L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPessoaEmpresaColaborador")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "DATAENTRADA")
    private LocalDate dataEntrada;

    @Column(name = "DATASAIDA")
    private LocalDate dataSaida;

    @Column(name = "CIVEL")
    private Boolean civel;

    @Column(name = "CRIMINAL")
    private Boolean criminal;

    @Column(name = "TRABALHISTA")
    private Boolean trabalhista;

    @Column(name = "TRIBUTARIO")
    private Boolean tributario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_EMPRESA")
    private Pessoa empresa;

    public PessoaEmpresaColaborador() {
    }

    public PessoaEmpresaColaborador(Long id) {
        this.id = id;
    }

    public PessoaEmpresaColaborador(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        PessoaEmpresaColaborador that = (PessoaEmpresaColaborador) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
