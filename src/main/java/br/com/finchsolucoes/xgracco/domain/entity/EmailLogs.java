package br.com.finchsolucoes.xgracco.domain.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author guilhermecamargo
 */

@Entity
@Table(name =  "EMAIL_LOGS")
@SequenceGenerator(allocationSize = 1, name = "seqEmail", sequenceName = "SEQ_EMAIL_LOGS")
@Data
@Builder
@AllArgsConstructor
public class EmailLogs extends Entidade{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqEmail")
    @Column(name = "ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_EXECUCAO")
    private Calendar dataExecucao;

    @Column(name = "NOME_TAREFA",length = 255, nullable = false)
    private String nomeTarefa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USUARIO_DESTINO")
    private Pessoa usuarioDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USUARIO_LOGADO")
    private Pessoa usuarioLogado;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @Column(name = "ENVIADO")
    private boolean enviado;

    @Column(name = "EXCECAO",length = 255)
    private String excecao;

    @PrePersist
    protected void onCreate() {
        dataExecucao = Calendar.getInstance();
    }

    public EmailLogs() {
    }

    @QueryProjection
    public EmailLogs(Calendar dataExecucao, String nomeTarefa, Pessoa usuarioDestino, Pessoa usuarioLogado, Processo processo, boolean enviado, String excecao) {
        this.dataExecucao = dataExecucao;
        this.nomeTarefa = nomeTarefa;
        this.usuarioDestino = usuarioDestino;
        this.usuarioLogado = usuarioLogado;
        this.processo = processo;
        this.enviado = enviado;
        this.excecao = excecao;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailLogs emailLogs = (EmailLogs) o;

        return id.equals(emailLogs.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "EmailLogs{" +
                "id=" + id +
                ", dataExecucao=" + dataExecucao +
                ", nomeTarefa='" + nomeTarefa + '\'' +
                ", usuarioDestino=" + usuarioDestino +
                ", usuarioLogado=" + usuarioLogado +
                ", processo=" + processo +
                ", enviado=" + enviado +
                ", excecao='" + excecao + '\'' +
                '}';
    }
}