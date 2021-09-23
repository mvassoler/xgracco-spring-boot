package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumJuros;
import br.com.finchsolucoes.xgracco.domain.enums.EnumPeriodicidadeJuros;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumJurosConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumPeriodicidadeJurosConverter;
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
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Relation(collectionRelation = "juros")
@Table(name = "JUROS", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao"}, name = "jurosDescricao")})
@SequenceGenerator(allocationSize = 1, name = "seqJuros", sequenceName = "SEQ_JUROS")
@RelatorioInterface(titulo = "Tabela de Juros")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Juros extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqJuros")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO")
    private String descricao;

    @NotNull
    @RelatorioInterface(titulo = "Percentual", padrao = true)
    @Column(name = "PERCENTUAL", precision = 19, scale = 4)
    private BigDecimal percentual;

    @NotNull
    @Column(name = "FK_PERIODOJUROSCORRECAO")
    @Convert(converter = EnumPeriodicidadeJurosConverter.class)
    private EnumPeriodicidadeJuros periodoJurosCorrecao;

    @NotNull
    @RelatorioInterface(titulo = "Tipos", padrao = true)
    @Column(name = "TIPOJUROS")
    @Convert(converter = EnumJurosConverter.class)
    private EnumJuros tipoJuros;

    @Transient
    private LogAuditoria logAuditoria;

    public Juros() {
    }

    @QueryProjection
    public Juros(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Juros(Long id, String descricao, BigDecimal percentual, EnumJuros tipoJuros, EnumPeriodicidadeJuros periodoJurosCorrecao) {
        this.id = id;
        this.descricao = descricao;
        this.percentual = percentual;
        this.tipoJuros = tipoJuros;
        this.periodoJurosCorrecao = periodoJurosCorrecao;
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
        Juros juros = (Juros) o;
        return Objects.equals(this.getId(), juros.getId());
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
