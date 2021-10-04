package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumInstanciaConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoJusticaConverter;
import br.com.finchsolucoes.xgracco.core.validation.Exists;
import br.com.finchsolucoes.xgracco.core.validation.Unique;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Relation(collectionRelation = "foros")
@Unique({"descricao", "comarca"})
@Table(name = "FORO", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao", "FK_COMARCA"}, name = "grupoForo")})
@SequenceGenerator(allocationSize = 1, name = "seqForo", sequenceName = "SEQ_FORO")
@RelatorioInterface(titulo = "Foros")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Foro extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqForo")
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO", length = 100, nullable = false)
    @NotEmpty(message = "cadastroForo.mensagem.erro1")
    private String descricao;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = EnumInstancia.class)
    @CollectionTable(name = "FORO_INSTANCIA", joinColumns = @JoinColumn(name = "FORO_ID"))
    @Column(name = "INSTANCIA_ID")
    @NotAudited
    @Convert(converter = EnumInstanciaConverter.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<EnumInstancia> instancias;

    @ElementCollection(targetClass = EnumTipoJustica.class)
    @CollectionTable(name = "FORO_TIPOJUSTICA", joinColumns = @JoinColumn(name = "FORO_ID"))
    @Column(name = "TIPOJUSTICA_ID")
    @Convert(converter = EnumTipoJusticaConverter.class)
    @NotAudited
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<EnumTipoJustica> tiposJustica;

    @Exists
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_COMARCA")
    private Comarca comarca;

    @Transient
    private Long idInstanciaPesquisaForo;

    @Transient
    private Long idTiposJusticaPesquisaForo;

    @Transient
    private LogAuditoria logAuditoria;

    public Foro() {
    }

    public Foro(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Foro(Long id, String descricao, Comarca comarca) {
        this.id = id;
        this.descricao = descricao;
        this.comarca = comarca;
    }

    @QueryProjection
    public Foro(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Foro(String descricao, Comarca comarca, List<EnumInstancia> instancias, List<EnumTipoJustica> tiposJustica) {
        this.descricao = descricao;
        this.comarca = comarca;
        this.instancias = instancias;
        this.tiposJustica = tiposJustica;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public String toString() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Foro that = (Foro) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
