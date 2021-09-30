package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Jordano
 */
@Entity
@Table(name = "INFORMACOESADICIONAIS")
@SequenceGenerator(allocationSize = 1, name = "seqInformacoesAdicionais", sequenceName = "SEQ_INFORMACOESADICIONAIS")
@RelatorioInterface(titulo = "Informações Complementares")
@Audited
@Data
@Builder
@AllArgsConstructor
public class InformacoesAdicionais extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    private static final long serialVersionUID = 7747054880983118075L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqInformacoesAdicionais")
    @Column(name = "ID")
    @RelatorioInterface(titulo = "ID Informação", label = "ID Informação")
    private Long id;

    @RelatorioInterface(titulo = "Conteúdo Informação", padrao = true, label = "Conteúdo Informação")
    @Column(name = "DESCRICAO", length = 100, nullable = false)
    private String conteudo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CAMPO")
    private Campo campo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_GARANTIA")
    private ProcessoGarantia processoGarantia;

    @Transient
    private Long idUsuario;

    @Transient
    private Long idProcesso;

    @Transient
    private LogAuditoria logAuditoria;

    public InformacoesAdicionais() {
    }

    @QueryProjection
    public InformacoesAdicionais(Long id, Campo campo, String conteudo, Processo processo) {
        this.id = id;
        this.campo = campo;
        this.conteudo = conteudo;
        this.processo = processo;
    }

    public InformacoesAdicionais(String conteudo, Campo campo, Processo processo) {
        this.conteudo = conteudo;
        this.campo = campo;
        this.processo = processo;
    }

    public InformacoesAdicionais(Long id) {
        this.id = id;
    }

    @Override
    public Long getPK() {
        return this.id;
    }

    @Override
    public String getTextoLog() {
        return this.conteudo;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        InformacoesAdicionais that = (InformacoesAdicionais) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
