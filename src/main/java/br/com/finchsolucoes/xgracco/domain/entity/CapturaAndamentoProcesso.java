package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Objects;

/**
 * Entidade que representa um item do agrupamento {@link CapturaAndamento}, onde é descrito cada
 * {@link Processo} que faz parte do agrupamento e que será considerado na captura de novos andamentos.
 *
 * @author andre.baroni
 */
@Entity
@Relation(collectionRelation = "capturaAndamentoProcessos")
@Table(name = "captura_andamento_proc")
@SequenceGenerator(allocationSize = 1, name = "seqCapturaAndamentoProcesso", sequenceName = "seq_capturaandamentoproc")
@Audited
@Data
@Builder
@AllArgsConstructor
public class CapturaAndamentoProcesso implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqCapturaAndamentoProcesso")
    @Column(name = "id")
    private Long id;

    //@NotNull
    @JoinColumn(name = "fk_capturaandamento", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private CapturaAndamento capturaAndamento;

    //@NotNull
    @JoinColumn(name = "fk_processo", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Processo processo;

    //@NotNull
    @Column(name = "ativo")
    private boolean ativo;

    @Column(name = "ultimo_retorno")
    private String ultimoRetorno;

    @Transient
    private LogAuditoria logAuditoria;

    public CapturaAndamentoProcesso() {
        super();
        this.setAtivo(Boolean.TRUE);
    }

    public CapturaAndamentoProcesso(CapturaAndamento capturaAndamento, Processo processo) {
        this();
        this.setCapturaAndamento(capturaAndamento);
        this.setProcesso(processo);
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return this.logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CapturaAndamentoProcesso)) return false;
        CapturaAndamentoProcesso that = (CapturaAndamentoProcesso) o;
        return this.getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
