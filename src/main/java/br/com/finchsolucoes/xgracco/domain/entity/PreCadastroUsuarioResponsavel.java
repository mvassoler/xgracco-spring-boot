package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Classe que representa a entidade PRE_CADASTRO_USUARIO_RESPONSAVEL
 *
 * @author felipe.costa
 */
@Entity
@Audited
@Table(name = "PRE_CADASTRO_USUARIO_RESPONSAVEL")
@Relation(collectionRelation = "preCadastroUsuariosResponsaveis")
@SequenceGenerator(allocationSize = 1, name = "seqPreCadastroUsuarioResponsavel", sequenceName = "SEQ_PRE_CADASTRO_USUARIO_RESPONSAVEL")
@RelatorioInterface(titulo = "Pré Cadastro de Usuario Responsavel")
@Data
@Builder
@AllArgsConstructor
public class PreCadastroUsuarioResponsavel implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPreCadastroParte")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @Column(name = "DATA_INICIO")
    private LocalDateTime dataInicio;

    @Column(name = "DATA_FIM")
    private LocalDateTime dataFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PRE_CADASTRO_PROCESSO")
    private PreCadastroProcesso preCadastroProcesso;

    @Transient
    private LogAuditoria logAuditoria;

    public PreCadastroUsuarioResponsavel() {
    }

    @QueryProjection
    public PreCadastroUsuarioResponsavel(Long id) {
        this.id = id;
    }

    @QueryProjection
    public PreCadastroUsuarioResponsavel(Long id,
                                         Pessoa pessoa,
                                         LocalDateTime dataInicio,
                                         LocalDateTime dataFim,
                                         PreCadastroProcesso preCadastroProcesso) {
        this.id = id;
        this.pessoa = pessoa;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.preCadastroProcesso = preCadastroProcesso;
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
        PreCadastroUsuarioResponsavel parte = (PreCadastroUsuarioResponsavel) o;
        return Objects.equals(this.getId(), parte.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
