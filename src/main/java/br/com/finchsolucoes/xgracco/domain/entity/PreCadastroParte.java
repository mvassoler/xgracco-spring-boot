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
import java.util.Objects;

/**
 * Classe que representa a entidade PRE_CADASTRO_PARTE
 *
 * @author raphael.moreira
 */
@Entity
@Audited
@Table(name = "PRE_CADASTRO_PARTE")
@Relation(collectionRelation = "preCadastroPartes")
@SequenceGenerator(allocationSize = 1, name = "seqPreCadastroParte", sequenceName = "SEQ_PRE_CADASTRO_PARTE")
@RelatorioInterface(titulo = "Pré Cadastro de Partes")
@Data
@Builder
@AllArgsConstructor
public class PreCadastroParte implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPreCadastroParte")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TIPO_PARTE")
    private TipoParte tipoParte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PRE_CADASTRO_PROCESSO")
    private PreCadastroProcesso preCadastroProcesso;

    @Transient
    private LogAuditoria logAuditoria;

    public PreCadastroParte() {
    }

    @QueryProjection
    public PreCadastroParte(Long id) {
        this.id = id;
    }

    @QueryProjection
    public PreCadastroParte(Long id,
                            Pessoa pessoa,
                            TipoParte tipoParte,
                            PreCadastroProcesso preCadastroProcesso) {
        this.id = id;
        this.pessoa = pessoa;
        this.tipoParte = tipoParte;
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
        PreCadastroParte parte = (PreCadastroParte) o;
        return Objects.equals(this.getId(), parte.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
