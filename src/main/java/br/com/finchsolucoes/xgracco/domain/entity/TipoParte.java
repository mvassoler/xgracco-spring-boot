package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Audited
@Relation(collectionRelation = "tipos-parte")
@Table(name = "TIPOPARTE", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao"}, name = "descTpPar")})
@SequenceGenerator(allocationSize = 1, name = "seqTipoParte", sequenceName = "SEQ_TIPOPARTE")
@RelatorioInterface(titulo = "Tipos Partes")
@Data
@Builder
@AllArgsConstructor
public class TipoParte implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqTipoParte")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 200)
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO")
    private String descricao;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoParte")
    private List<ProcessoParte> partes;

    @Transient
    private LogAuditoria logAuditoria;

    public TipoParte() {
    }

    public TipoParte(Long id) {
        this.id = id;
    }

    @QueryProjection
    public TipoParte(Long id, String descricao) {
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
        TipoParte tipoParte = (TipoParte) o;
        return Objects.equals(this.getId(), tipoParte.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
