package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumMotivoDevolucaoTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumMotivoDevolucaoTarefaConverter;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "HISTORICO_FILA_PESSOA")
@SequenceGenerator(allocationSize = 1, name = "seqHistoricoFilaPessoa", sequenceName = "SEQ_HISTORICO_FILA_PESSOA")
@Data
@Builder
@AllArgsConstructor
public class HistoricoFilaPessoa implements Serializable {

    private static final long serialVersionUID = 4954537891254745008L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqHistoricoFilaPessoa")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FILA", insertable = false, updatable = false)
    private Fila fila;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA", insertable = false, updatable = false)
    private Pessoa pessoa;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATAINICIO", nullable = true)
    private Calendar dataInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATACONCLUSAO", nullable = true)
    private Calendar dataConclusao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATADEVOLUCAO", nullable = true)
    private Calendar dataDevolucao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATALIBERACAO", nullable = true)
    private Calendar dataLiberacao;

    @Column(name = "MOTIVODEVOLUCAO", nullable = true)
    @Convert(converter = EnumMotivoDevolucaoTarefaConverter.class)
    private EnumMotivoDevolucaoTarefa motivoDevolucao;

    @Lob
    @Column(name = "DESCRICAODEVOLUCAO", columnDefinition="CLOB")
    private String descricaoDevolucao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHOTAREFA")
    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    @Column(name = "FK_TAREFA")
    private Long idTarefa;

    @Column(name = "FK_FILA")
    private Long idFila;

    @Column(name = "FK_PESSOA")
    private Long idPessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DADOSBASICOSTAREFA")
    private DadosBasicosTarefa dadosBasicosTarefa;

    public HistoricoFilaPessoa() {
    }

    public HistoricoFilaPessoa(Long id) {
        this.id = id;
    }

    public void setFila(Fila fila) {
        this.fila = fila;
        if (fila != null) {
            this.idFila = fila.getId();
        }
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        if (pessoa != null) {
            this.idPessoa = pessoa.getId();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        HistoricoFilaPessoa that = (HistoricoFilaPessoa) o;
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
