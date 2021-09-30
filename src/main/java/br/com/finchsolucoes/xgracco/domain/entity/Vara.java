package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumInstanciaConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoJusticaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Relation(collectionRelation = "varas")
@Table(name = "VARA", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao"}, name = "descVara")})
@SequenceGenerator(allocationSize = 1, name = "seqVara", sequenceName = "SEQ_VARA")
@RelatorioInterface(titulo = "Vara")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Vara extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqVara")
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO", length = 100, nullable = false)
    @NotEmpty(message = "cadastroVara.mensagem.erro1")
    private String descricao;

    @ElementCollection(targetClass = EnumTipoJustica.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "VARA_TIPOJUSTICA", joinColumns = @JoinColumn(name = "VARA_ID"))
    @Column(name = "TIPOJUSTICA_ID")
    @Convert(converter = EnumTipoJusticaConverter.class)
    @NotAudited
    private List<EnumTipoJustica> tiposJustica;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ElementCollection(fetch = FetchType.LAZY, targetClass = EnumInstancia.class)
    @CollectionTable(name = "VARA_INSTANCIA", joinColumns = @JoinColumn(name = "VARA_ID"))
    @Column(name = "INSTANCIA_ID")
    @NotAudited
    @Convert(converter = EnumInstanciaConverter.class)
    private List<EnumInstancia> instancias;

    @Transient
    private LogAuditoria logAuditoria;

    public Vara() {
    }

    public Vara(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Vara(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Vara(String descricao, List<EnumTipoJustica> tiposJustica, List<EnumInstancia> instancias) {
        this.descricao = descricao;
        this.tiposJustica = tiposJustica;
        this.instancias = instancias;
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
        Vara that = (Vara) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
