package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Collection;
import java.util.Objects;

/**
 * Entidade responsável por salvar as informações de uma solicitação criada no catcher.
 *
 * @author andre.baroni
 */
@Entity
@Relation(collectionRelation = "solicitacaoAndamentos")
@Table(name = "solicitacao_andamento")
@SequenceGenerator(allocationSize = 1, name = "seqSolicitacaoAndamento", sequenceName = "seq_solicitacaoandamento")
@Audited
@Data
@Builder
@AllArgsConstructor
public class SolicitacaoAndamento implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqSolicitacaoAndamento")
    @Column(name = "id")
    private Long id;

    @NotNull
    @JoinColumn(name = "fk_capturaandamento", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CapturaAndamento capturaAndamento;

    @NotNull
    @Column(name = "solicitacao", nullable = false)
    private String solicitacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_solicitacao", nullable = false)
    private Calendar dataSolicitacao;

    @OneToMany(mappedBy = "solicitacaoAndamento", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JsonManagedReference
    private Collection<SolicitacaoAndamentoLog> solicitacaoAndamentoLogs;

    @Transient
    @JsonProperty("quantidadeProcessosEnviados")
    private Long quantidadeProcessosEnviados;

    @Transient
    @JsonProperty("quantidadeProcessosEncontrados")
    private Long quantidadeProcessosEncontrados;

    @Transient
    @JsonProperty("quantidadeNovosAndamentos")
    private Long quantidadeNovosAndamentos;

    @Transient
    private LogAuditoria logAuditoria;

    public SolicitacaoAndamento() {
        super();
    }

    public SolicitacaoAndamento(CapturaAndamento capturaAndamento, String solicitacao, Calendar dataSolicitacao) {
        this();
        this.setCapturaAndamento(capturaAndamento);
        this.setSolicitacao(solicitacao);
        this.setDataSolicitacao(dataSolicitacao);
    }

    @QueryProjection
    public SolicitacaoAndamento(Long id, CapturaAndamento capturaAndamento, String solicitacao,
                                Calendar dataSolicitacao, Long quantidadeProcessosEnviados,
                                Long quantidadeProcessosEncontrados, Long quantidadeNovosAndamentos) {
        this();
        this.setId(id);
        this.setCapturaAndamento(capturaAndamento);
        this.setSolicitacao(solicitacao);
        this.setDataSolicitacao(dataSolicitacao);
        this.setQuantidadeProcessosEnviados(quantidadeProcessosEnviados);
        this.setQuantidadeProcessosEncontrados(quantidadeProcessosEncontrados);
        this.setQuantidadeNovosAndamentos(quantidadeNovosAndamentos);
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
        SolicitacaoAndamento that = (SolicitacaoAndamento) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
