package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoProcesso;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoProcessoConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author enedycordeiro
 */
@Entity
@Relation(collectionRelation = "fases")
@Table(name = "FASE", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao", "FK_TIPO_PROCESSO"}, name = "grupoFase")})
@SequenceGenerator(allocationSize = 1, name = "seqFase", sequenceName = "SEQ_FASE")
@Audited
@RelatorioInterface(titulo = "Fases")
@Data
public class Fase extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqFase")
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO", length = 100, nullable = false)
    private String descricao;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "PRATICA_FASE", joinColumns = @JoinColumn(name = "FASE_ID"), inverseJoinColumns = @JoinColumn(name = "PRATICA_ID"))
    @NotAudited
    private List<Pratica> praticas;

    @RelatorioInterface(titulo = "Tipo", padrao = true)
    @Column(name = "FK_TIPO_PROCESSO")
    @Convert(converter = EnumTipoProcessoConverter.class)
    private EnumTipoProcesso tipoProcesso;

    @Transient
    private Long idPraticaFiltroPesquisa;

    @Transient
    private LogAuditoria logAuditoria;

    public Fase() {
    }

    public Fase(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Fase(Long id, String descricao, EnumTipoProcesso tipoProcesso) {
        this.id = id;
        this.descricao = descricao;
        this.tipoProcesso = tipoProcesso;
    }

    @QueryProjection
    public Fase(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Fase fase = (Fase) o;
        return Objects.equals(this.getId(), fase.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
