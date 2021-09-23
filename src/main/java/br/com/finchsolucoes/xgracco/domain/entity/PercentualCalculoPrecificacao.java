package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 *
 * @author guilhermecamargo
 */
@Entity
@Table(name = "PERCENTUAL_CALCULO_PRECIFICACAO", uniqueConstraints = {@UniqueConstraint(columnNames = {"DESCRICAO"}, name = "percentualDescricao")})
@Relation(collectionRelation = "percentuais-precificacoes")
@SequenceGenerator(allocationSize = 1, name = "seqPercCalculoPrecificacao", sequenceName = "SEQ_PERCENTUAL_CALCULO_PRECIFICACAO")
@Audited
@Data
@Builder
@AllArgsConstructor
public class PercentualCalculoPrecificacao extends Entidade implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPercCalculoPrecificacao")
    @Column(name = "ID")
    private Long id;

    @Size(max = 100)
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO")
    private String descricao;

    @RelatorioInterface(titulo = "Percentual", padrao = true)
    @Column(name = "PERCENTUAL", precision = 19, scale = 4)
    private BigDecimal percentual;

    @Transient
    private LogAuditoria logAuditoria;

    public PercentualCalculoPrecificacao() {
    }

    @QueryProjection
    public PercentualCalculoPrecificacao(Long id){
        this.id = id;
    }

    @QueryProjection
    public PercentualCalculoPrecificacao(Long id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    @QueryProjection
    public PercentualCalculoPrecificacao(Long id, String descricao, BigDecimal percentual){
        this.id = id;
        this.descricao = descricao;
        this.percentual = percentual;
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
        if (o == null || getClass() != o.getClass()) return false;

        PercentualCalculoPrecificacao that = (PercentualCalculoPrecificacao) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return descricao != null ? descricao.equals(that.descricao) : that.descricao == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PercentualCalculoPrecificacao{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", percentual=" + percentual +
                '}';
    }
}
