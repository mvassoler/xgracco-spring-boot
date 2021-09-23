package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
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
 * Classe que representa a entidade PRE_CADASTRO_INFO_ADICIONAL
 *
 * @author raphael.moreira
 */
@Entity
@Table(name = "PRE_CADASTRO_INFO_ADICIONAL")
@SequenceGenerator(allocationSize = 1, name = "seqPreCadastroInfoAdicional", sequenceName = "SEQ_PRE_CADASTRO_INFO_ADICIONAL")
@RelatorioInterface(titulo = "Pré Cadastro Informações Complementares")
@Audited
@Data
@Builder
@AllArgsConstructor
public class PreCadastroInfoAdicional implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPreCadastroInfoAdicional")
    @Column(name = "ID")
    @RelatorioInterface(titulo = "ID Informação", label = "ID Informação")
    private Long id;

    @RelatorioInterface(titulo = "Conteúdo Informação", padrao = true, label = "Conteúdo Informação")
    @Column(name = "CONTEUDO", length = 100, nullable = false)
    private String conteudo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CAMPO")
    private Campo campo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PRE_CADASTRO_PROCESSO")
    private PreCadastroProcesso preCadastroProcesso;

    @Transient
    private LogAuditoria logAuditoria;

    public PreCadastroInfoAdicional() {
    }

    @QueryProjection
    public PreCadastroInfoAdicional(Long id,
                                    Campo campo,
                                    String conteudo,
                                    PreCadastroProcesso preCadastroProcesso) {
        this.id = id;
        this.campo = campo;
        this.conteudo = conteudo;
        this.preCadastroProcesso = preCadastroProcesso;
    }

    @QueryProjection
    public PreCadastroInfoAdicional(String conteudo,
                                    Campo campo,
                                    PreCadastroProcesso preCadastroProcesso) {
        this.conteudo = conteudo;
        this.campo = campo;
        this.preCadastroProcesso = preCadastroProcesso;
    }

    @QueryProjection
    public PreCadastroInfoAdicional(Long id,
                                    String conteudo,
                                    Campo campo,
                                    Pessoa pessoa,
                                    PreCadastroProcesso preCadastroProcesso) {
        this.id = id;
        this.conteudo = conteudo;
        this.campo = campo;
        this.pessoa = pessoa;
        this.preCadastroProcesso = preCadastroProcesso;
    }

    @QueryProjection
    public PreCadastroInfoAdicional(Long id) {
        this.id = id;
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
        PreCadastroInfoAdicional that = (PreCadastroInfoAdicional) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
