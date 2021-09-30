package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Table(name = "PROCESSOAUDITORIA")
@SequenceGenerator(allocationSize = 1, name = "seqProcessoAuditoria", sequenceName = "SEQ_PROCESSOAUDITORIA")
@Data
@Builder
public class ProcessoAuditoria extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqProcessoAuditoria")
    @Column(name = "ID")
    private Long id;

    @Column(name = "VALOR", precision = 19, scale = 2)
    private BigDecimal valor;

    @Column(name = "PROVISAO", precision = 19, scale = 2)
    private BigDecimal provisao;

    @Column(name = "VALORATUALIZADO", precision = 19, scale = 2)
    private BigDecimal valorAtualizado;

    @Column(name = "PROVISAOATUALIZADO", precision = 19, scale = 2)
    private BigDecimal provisaoAtualizado;

    public ProcessoAuditoria() {
    }

    @QueryProjection
    public ProcessoAuditoria(Long id,
                             BigDecimal valor,
                             BigDecimal provisao,
                             BigDecimal valorAtualizado,
                             BigDecimal provisaoAtualizado) {
        this.id = id;
        this.valor = valor;
        this.provisao = provisao;
        this.valorAtualizado = valorAtualizado;
        this.provisaoAtualizado = provisaoAtualizado;
    }

    public ProcessoAuditoria(Long id) {
        this.id = id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ProcessoAuditoria that = (ProcessoAuditoria) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
