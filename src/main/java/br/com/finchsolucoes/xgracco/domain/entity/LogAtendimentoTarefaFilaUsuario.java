package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumAcaoLogAtendimentoTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumAcaoLogAtendimentoTarefaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade responsável por logar os atendimentos de tarefas em uma fila.
 *
 * @author paulo.marcon
 * @since 5.4.0
 */
@Entity
@Audited
@Relation(collectionRelation = "log-atendimentos")
@Table(name = "LOG_ATENDIMENTO_TAREFA_FILA_USUARIO")
@RelatorioInterface(titulo = "Tempo de tratamento de tarefa")
@SequenceGenerator(allocationSize = 1, name = "seqLogAtendimentoTarefaFilaUsuario", sequenceName = "SEQ_LOG_ATENDIMENTO_TAREFA_FILA_USUARIO")
@Data
@Builder
@AllArgsConstructor
public class LogAtendimentoTarefaFilaUsuario implements Serializable, EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqLogAtendimentoTarefaFilaUsuario")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DATA_INICIO")
    @NotNull
    private LocalDateTime dataInicio;

    @Column(name = "DATA_ENCERRAMENTO")
    private LocalDateTime dataEncerramento;

    @Column(name = "FK_ACAO")
    @NotNull
    @Convert(converter = EnumAcaoLogAtendimentoTarefaConverter.class)
    @RelatorioInterface(titulo = "Status do Atendimento")
    private EnumAcaoLogAtendimentoTarefa acao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @RelatorioInterface(titulo = "Tarefa")
    @JoinColumn(name = "FK_DADOSBASICOSTAREFA")
    private DadosBasicosTarefa dadosBasicosTarefa;

    @NotNull
    @JoinColumn(name = "FK_USUARIO")
    @ManyToOne(fetch = FetchType.LAZY)
    @RelatorioInterface(titulo = "Usuário")
    private Usuario usuario;

    @JoinColumn(name = "FK_FILA")
    @ManyToOne(fetch = FetchType.LAZY)
    @RelatorioInterface(titulo = "Fila")
    private Fila fila;

    @Transient
    private LogAuditoria logAuditoria;

    public LogAtendimentoTarefaFilaUsuario() {
    }

    public LogAtendimentoTarefaFilaUsuario(LocalDateTime dataInicio, EnumAcaoLogAtendimentoTarefa acao,
                                           DadosBasicosTarefa dadosBasicosTarefa, Usuario usuario, Fila fila) {
        this.dataInicio = dataInicio;
        this.acao = acao;
        this.dadosBasicosTarefa = dadosBasicosTarefa;
        this.usuario = usuario;
        this.fila = fila;
    }

    public LogAtendimentoTarefaFilaUsuario(LocalDateTime dataInicio, LocalDateTime dataEncerramento, EnumAcaoLogAtendimentoTarefa acao,
                                           DadosBasicosTarefa dadosBasicosTarefa, Usuario usuario, Fila fila) {
        this.dataInicio = dataInicio;
        this.dataEncerramento = dataEncerramento;
        this.acao = acao;
        this.dadosBasicosTarefa = dadosBasicosTarefa;
        this.usuario = usuario;
        this.fila = fila;
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
        if (o == null || getClass() != o.getClass()) return false;
        LogAtendimentoTarefaFilaUsuario that = (LogAtendimentoTarefaFilaUsuario) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LogAtendimentoTarefaFilaUsuario{" +
                "dataInicio=" + dataInicio +
                ", acao=" + acao +
                ", dadosBasicosTarefa=" + dadosBasicosTarefa +
                ", usuario=" + usuario +
                ", fila=" + fila +
                '}';
    }
}
