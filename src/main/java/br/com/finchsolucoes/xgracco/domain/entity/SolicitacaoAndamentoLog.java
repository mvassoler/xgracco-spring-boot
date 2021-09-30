package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.SolicitacaoAndamentoLogStatus;
import br.com.finchsolucoes.xgracco.domain.enums.converter.SolicitacaoAndamentoLogStatusConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Objects;

/**
 * Entidade responsavel por descriminar todos os processos enviados numa nova solicitação de captura de novos
 * andamentos.
 *
 * @author andre.baroni
 */
@Entity
@Relation(collectionRelation = "solicitacaoAndamentoLogs")
@Table(name = "solicitacao_andamento_log")
@SequenceGenerator(allocationSize = 1, name = "seqSolicitacaoAndamentoLog", sequenceName = "seq_solicitacaoandamentolog")
@Audited
@Data
@Builder
@AllArgsConstructor
public class SolicitacaoAndamentoLog implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqSolicitacaoAndamentoLog")
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "fk_solicitacaoandamento", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    private SolicitacaoAndamento solicitacaoAndamento;

    @JoinColumn(name = "fk_processo", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Processo processo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "status", nullable = false)
    @Convert(converter = SolicitacaoAndamentoLogStatusConverter.class)
    private SolicitacaoAndamentoLogStatus status;

    @Column(name = "quantidade_novo_andamento", nullable = false)
    private Long quantidadeNovoAndamento;

    @Transient
    private LogAuditoria logAuditoria;

    public SolicitacaoAndamentoLog() {
        super();
        this.setStatus(SolicitacaoAndamentoLogStatus.ENVIADO);
        this.setQuantidadeNovoAndamento(0L);
    }

    public SolicitacaoAndamentoLog(SolicitacaoAndamento solicitacaoAndamento, Processo processo) {
        this();
        this.setSolicitacaoAndamento(solicitacaoAndamento);
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
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        SolicitacaoAndamentoLog that = (SolicitacaoAndamentoLog) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
