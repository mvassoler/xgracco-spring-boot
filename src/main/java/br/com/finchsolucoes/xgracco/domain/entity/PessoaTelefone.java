package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoTelefone;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoTelefoneConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Audited
@Relation(collectionRelation = "telefones")
@Table(name = "PESSOA_TELEFONE")
@SequenceGenerator(allocationSize = 1, name = "seqPessoaTelefone", sequenceName = "SEQ_PESSOA_TELEFONE")
@Data
@Builder
@AllArgsConstructor
public class PessoaTelefone implements EntidadeAuditada, PessoaDependency {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPessoaTelefone")
    @Column(name = "ID")
    private Long id;

    //@NotBlank
    @Size(max = 100)
    @Column(name = "NUMERO")
    private String numero;

    //@NotBlank
    @Size(max = 100)
    @Column(name = "CONTATO")
    private String nomeContato;

    @NotNull
    @Column(name = "PADRAO")
    private boolean padrao;

    //@NotNull
    @Column(name = "FK_TIPOTELEFONE")
    @Convert(converter = EnumTipoTelefoneConverter.class)
    private EnumTipoTelefone tipoTelefone;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @Transient
    private LogAuditoria logAuditoria;

    public PessoaTelefone() {
    }

    public PessoaTelefone(Long id) {
        this.id = id;
    }

    public PessoaTelefone(Pessoa pessoa) {
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
        PessoaTelefone telefone = (PessoaTelefone) o;
        return Objects.equals(this.getId(), telefone.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
