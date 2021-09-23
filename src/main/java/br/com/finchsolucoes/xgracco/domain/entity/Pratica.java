package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoProcesso;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumAreaConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoProcessoConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
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
@Entity
@Relation(collectionRelation = "praticas")
@Table(name = "PRATICA", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao", "FK_AREA"}, name = "grupoPratica")})
@SequenceGenerator(allocationSize = 1, name = "seqPratica", sequenceName = "SEQ_PRATICA")
@RelatorioInterface(titulo = "Práticas Jurídicas")
@Audited
public class Pratica extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPratica")
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO", length = 100, nullable = false)
    private String descricao;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = EnumTipoProcesso.class)
    @CollectionTable(name = "PRATICA_TIPOPROCESSO", joinColumns = @JoinColumn(name = "PRATICA_ID"))
    @Column(name = "TIPOPROCESSO_ID")
    @Convert(converter = EnumTipoProcessoConverter.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotAudited
    private List<EnumTipoProcesso> tipoProcesso;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "REL_REPARTICAO_PRATICA", joinColumns = @JoinColumn(name = "PRATICA_ID"), inverseJoinColumns = @JoinColumn(name = "REPARTICAO_ID"))
    @Fetch(FetchMode.SUBSELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotAudited
    private List<Reparticao> reparticao;

    @RelatorioInterface(ignore = true)
    @Column(name = "FK_AREA")
    @Convert(converter = EnumAreaConverter.class)
    private EnumArea area;

    @Transient
    private LogAuditoria logAuditoria;

    public Pratica() {
    }

    public Pratica(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Pratica(Long id, String descricao, EnumArea area) {
        this.id = id;
        this.descricao = descricao;
        this.area = area;
    }

    @QueryProjection
    public Pratica(Long id, String descricao, EnumArea area,List<EnumTipoProcesso> tipoProcesso) {
        this.id = id;
        this.descricao = descricao;
        this.area = area;
        this.tipoProcesso = tipoProcesso;
    }

    @QueryProjection
    public Pratica(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Pratica(String descricao, EnumArea area,List<EnumTipoProcesso> tipoProcesso) {
        this.descricao = descricao;
        this.area = area;
        this.tipoProcesso = tipoProcesso;
    }

    @Override
    public Long getId() {
        return id;
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
        StringBuilder sb = new StringBuilder();
        if (area != null) {
            sb.append(area.getDescricao()).append(" - ");
        }
        sb.append(descricao);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Pratica that = (Pratica) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
