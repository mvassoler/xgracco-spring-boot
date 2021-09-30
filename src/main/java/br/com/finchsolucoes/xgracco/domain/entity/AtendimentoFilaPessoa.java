package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ATENDIMENTO_FILA_PESSOA")
@SequenceGenerator(allocationSize = 1, name = "seqAtendimentoFilaPessoa", sequenceName = "SEQ_ATENDIMENTO_FILA_PESSOA")
@Data
@Builder
@AllArgsConstructor
public class AtendimentoFilaPessoa implements Serializable {

    private static final long serialVersionUID = 3507417900800125926L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqAtendimentoFilaPessoa")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FILA")
    private Fila fila;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA", unique = true, insertable = false, updatable = false)
    private Pessoa pessoa;

    @Column(name = "FK_PESSOA", unique = true)
    private Long idPessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHOTAREFA")
    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    @Column(name = "CASEEXECUTIONID")
    private String caseExecutionId;

    @Column(name = "CASEINSTANCEID")
    private String caseInstanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DADOSBASICOSTAREFA")
    private DadosBasicosTarefa dadosBasicosTarefa;

    public AtendimentoFilaPessoa() {
    }

    public AtendimentoFilaPessoa(Long id) {
        this.id = id;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        AtendimentoFilaPessoa that = (AtendimentoFilaPessoa) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
