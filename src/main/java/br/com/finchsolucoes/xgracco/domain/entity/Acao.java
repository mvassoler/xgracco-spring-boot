package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumInstanciaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Data
@Entity
@Relation(collectionRelation = "acoes")
@Table(name = "ACAO", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao"}, name = "acaoDescricao")})
@SequenceGenerator(allocationSize = 1, name = "seqAcao", sequenceName = "SEQ_ACAO")
@RelatorioInterface(titulo = "Ações")
@Audited
public class Acao implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqAcao")
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO", length = 100, nullable = false)
    //@NotBlank(message = "cadastroAcao.mensagem.erro1")
    //@Size(min = 1, max = 100)
    private String descricao;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = EnumInstancia.class)
    @CollectionTable(name = "INSTANCIA_ACAO", joinColumns = @JoinColumn(name = "ACAO_ID"))
    @Column(name = "INSTANCIA_ID")
    @NotAudited
    @Convert(converter = EnumInstanciaConverter.class)
    private List<EnumInstancia> instancias;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "PRATICA_ACAO", joinColumns = @JoinColumn(name = "ACAO_ID"), inverseJoinColumns = @JoinColumn(name = "PRATICA_ID"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotAudited
    private List<Pratica> praticas;

    @Transient
    private LogAuditoria logAuditoria;

    public Acao() {
    }

    public Acao(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Acao(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @QueryProjection
    public Acao(Long id, String descricao, List<Pratica> praticas) {
        this.id = id;
        this.descricao = descricao;
        this.praticas = praticas;
    }

    public Acao(String descricao, List<Pratica> praticas, List<EnumInstancia> instancias) {
        this.descricao = descricao;
        this.praticas = praticas;
        this.instancias = instancias;
    }

    @Override
    public String toString() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Acao that = (Acao) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }
}
