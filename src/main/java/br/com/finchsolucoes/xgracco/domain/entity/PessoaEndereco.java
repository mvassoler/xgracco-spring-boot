package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoEndereco;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoEnderecoConverter;
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
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Andre Farina
 */
@Entity
@Relation(collectionRelation = "enderecos")
@Table(name = "PESSOA_ENDERECO")
@SequenceGenerator(allocationSize = 1, name = "seqPessoaEndereco", sequenceName = "SEQ_PESSOA_ENDERECO")
@Data
@Builder
@AllArgsConstructor
public class PessoaEndereco implements PessoaDependency, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPessoaEndereco")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "LOGRADOURO")
    private String logradouro;

    @NotBlank
    @Size(max = 255)
    @Column(name = "NUMERO")
    private String numero;

    @Size(max = 100)
    @Column(name = "COMPLEMENTO")
    private String complemento;

    @NotBlank
    @Size(max = 100)
    @Column(name = "BAIRRO")
    private String bairro;

    @NotBlank
    @Size(max = 10)
    @Column(name = "CEP")
    private String cep;

    @NotNull
    @Column(name = "PADRAO")
    private boolean padrao;

    @Convert(converter = EnumTipoEnderecoConverter.class)
    @Column(name = "FK_TIPOENDERECO")
    private EnumTipoEndereco tipoEndereco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TIPOLOGRADOURO")
    private TipoLogradouro tipoLogradouro;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CIDADE")
    private Cidade cidade;

    public PessoaEndereco() {
    }

    public PessoaEndereco(Long id) {
        this.id = id;
    }

    public PessoaEndereco(Pessoa pessoa) {
        this.pessoa = pessoa;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        PessoaEndereco endereco = (PessoaEndereco) o;
        return Objects.equals(this.getId(), endereco.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
