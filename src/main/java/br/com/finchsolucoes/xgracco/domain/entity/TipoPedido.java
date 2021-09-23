package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumAreaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Table(name = "TIPOPEDIDO", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao", "FK_AREA"}, name = "grupoTpPed")})
@SequenceGenerator(allocationSize = 1, name = "seqTipoPedido", sequenceName = "SEQ_TIPOPEDIDO")
@Relation(collectionRelation = "tipos-pedido")
@RelatorioInterface(titulo = "Tipos de Pedido")
@Audited
@Data
@Builder
@AllArgsConstructor
public class TipoPedido implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqTipoPedido")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO")
    private String descricao;

    @NotNull
    @Column(name = "FK_AREA")
    @Convert(converter = EnumAreaConverter.class)
    @RelatorioInterface(titulo = "Área", padrao = true)
    private EnumArea area;

    @Transient
    private LogAuditoria logAuditoria;

    public TipoPedido() {
    }

    public TipoPedido(Long id) {
        this.id = id;
    }

    @QueryProjection
    public TipoPedido(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @QueryProjection
    public TipoPedido(Long id, String descricao, EnumArea area) {
        this.id = id;
        this.descricao = descricao;
        this.area = area;
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
        TipoPedido that = (TipoPedido) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return descricao;
    }
}
