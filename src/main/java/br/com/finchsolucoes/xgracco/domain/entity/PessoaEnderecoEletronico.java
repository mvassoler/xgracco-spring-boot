package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoEnderecoEletronico;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoEnderecoEletronicoConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Audited
@Relation(collectionRelation = "enderecos-eletronicos")
@Table(name = "PESSOA_ENDERECO_ELETRONICO")
@SequenceGenerator(allocationSize = 1, name = "seqPessoaEnderecoEletronico", sequenceName = "SEQ_PESSOA_ENDERECO_ELETRONICO")
@Data
@Builder
@AllArgsConstructor
public class PessoaEnderecoEletronico implements EntidadeAuditada, PessoaDependency {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPessoaEnderecoEletronico")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "PADRAO")
    private boolean padrao;

    @Column(name = "TIPO")
    @Convert(converter = EnumTipoEnderecoEletronicoConverter.class)
    private EnumTipoEnderecoEletronico tipoEnderecoEletronico;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @Transient
    private LogAuditoria logAuditoria;

    public PessoaEnderecoEletronico() {
    }

    public PessoaEnderecoEletronico(Long id) {
        this.id = id;
    }

    public PessoaEnderecoEletronico(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return this.logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        PessoaEnderecoEletronico that = (PessoaEnderecoEletronico) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
