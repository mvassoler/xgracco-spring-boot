package br.com.finchsolucoes.xgracco.domain.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Thiago Fogar
 * @since 2.2.5
 */
@Entity
@Table(name = "SOLICITACAO_ARQUIVO")
@SequenceGenerator(allocationSize = 1, name = "seqSolicitacaoArquivo", sequenceName = "SEQ_SOLICITACAO_ARQUIVO")
@Data
@Builder
public class SolicitacaoArquivo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqSolicitacaoArquivo")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ARQUIVO", nullable = false)
    private Arquivo arquivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SOLICITACAO", nullable = false)
    private SolicitacaoLog solicitacaoLog;

    public SolicitacaoArquivo() {
    }

    public SolicitacaoArquivo(Arquivo arquivo, SolicitacaoLog solicitacaoLog) {
        this.arquivo = arquivo;
        this.solicitacaoLog = solicitacaoLog;
    }

    @QueryProjection
    public SolicitacaoArquivo(Long id, Arquivo arquivo, SolicitacaoLog solicitacaoLog) {
        this.id = id;
        this.arquivo = arquivo;
        this.solicitacaoLog = solicitacaoLog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SolicitacaoArquivo that = (SolicitacaoArquivo) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
