package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumMotivoDevolucaoTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusDevolucaoFila;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumMotivoDevolucaoTarefaConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumStatusDevolucaoFilaConverter;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author paulomarcon
 */
@Entity
@Table(name = "HISTORICO_FILA_DEVOLUCAO")
@SequenceGenerator(allocationSize = 1, name = "seqHistoricoFilaDevolucao", sequenceName = "SEQ_HISTORICO_FILA_DEVOLUCAO")
@Data
@Builder
@AllArgsConstructor
public class HistoricoFilaDevolucao implements Serializable {

    private static final long serialVersionUID = 3507417900800125926L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqHistoricoFilaDevolucao")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA_DEVOLUCAO")
    private Pessoa pessoaDevolucao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATADEVOLUCAO")
    private Calendar dataDevolucao;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "MOTIVODEVOLUCAO")
    @Convert(converter = EnumMotivoDevolucaoTarefaConverter.class)
    private EnumMotivoDevolucaoTarefa motivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA_TRATAMENTO")
    private Pessoa pessoaTratamento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATATRATAMENTO")
    private Calendar dataTratamento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATALIBERACAO")
    private Calendar dataLiberacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATAHIBERNACAO")
    private Calendar dataHibernacao;

    @Column(name = "STATUS")
    @Convert(converter = EnumStatusDevolucaoFilaConverter.class)
    private EnumStatusDevolucaoFila status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHOTAREFA")
    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "FK_FILA")
    private Fila fila;

    @Column(name = "CONCLUIDO")
    private Boolean devolucaoConcluida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_SOLICITACAO")
    private SolicitacaoLog solicitacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DADOSBASICOSTAREFA")
    private DadosBasicosTarefa dadosBasicosTarefa;

    public HistoricoFilaDevolucao() {
    }

    @QueryProjection
    public HistoricoFilaDevolucao(Long id, DadosBasicosTarefa dadosBasicosTarefa, Pessoa pessoaDevolucao, Calendar dataDevolucao,
                                  String descricao, EnumMotivoDevolucaoTarefa motivo,
                                  Pessoa pessoaTratamento, Calendar dataTratamento, Calendar dataLiberacao,
                                  Calendar dataHibernacao, EnumStatusDevolucaoFila status, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Fila fila,
                                  Boolean devolucaoConcluida, SolicitacaoLog solicitacao) {
        this.id = id;
        this.dadosBasicosTarefa = dadosBasicosTarefa;
        this.pessoaDevolucao = pessoaDevolucao;
        this.dataDevolucao = dataDevolucao;
        this.descricao = descricao;
        this.motivo = motivo;
        this.pessoaTratamento = pessoaTratamento;
        this.dataTratamento = dataTratamento;
        this.dataLiberacao = dataLiberacao;
        this.dataHibernacao = dataHibernacao;
        this.status = status;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.fila = fila;
        this.devolucaoConcluida = devolucaoConcluida;
        this.solicitacao = solicitacao;
    }

    @QueryProjection
    public HistoricoFilaDevolucao(String descricao, Calendar dataDevolucao, Calendar dataTratamento, EnumMotivoDevolucaoTarefa motivoDevolucaoTarefa, Pessoa pessoaDevolucao, Pessoa pessoaTratamento){
        this.descricao = descricao;
        this.dataDevolucao = dataDevolucao;
        this.dataTratamento = dataTratamento;
        this.motivo = motivoDevolucaoTarefa;
        this.pessoaDevolucao = pessoaDevolucao;
        this.pessoaTratamento = pessoaTratamento;
    }

    public HistoricoFilaDevolucao(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        HistoricoFilaDevolucao that = (HistoricoFilaDevolucao) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    public DadosBasicosTarefa getDadosBasicosTarefa() {
        return dadosBasicosTarefa;
    }

    public void setDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa) {
        this.dadosBasicosTarefa = dadosBasicosTarefa;
    }
}
