package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumOrigemIntegracaoWs;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @author Jordano
 */
@Entity
@Table(name = "PUBLICACAO")
@SequenceGenerator(allocationSize = 1, name = "seqPublicacao", sequenceName = "SEQ_PUBLICACAO")
@Relation(collectionRelation = "publicacoes")
@RelatorioInterface(titulo = "Publicações")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Publicacao extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPublicacao")
    @Column(name = "ID")
    @RelatorioInterface(ignore = true)
    private Long id;

    @RelatorioInterface(titulo = "Texto", padrao = true)
    @Column(name = "TEXTO", nullable = false)
    private String texto;

    @RelatorioInterface(titulo = "Data da Colagem", padrao = true)
    @Column(name = "DATA_COLAGEM", nullable = false)
    private LocalDateTime dataColagem;

    @JsonIgnore
    @Deprecated
    @RelatorioInterface(ignore = true)
    @Column(name = "DATACOLAGEM")
    private String dataColagemOld;

    @RelatorioInterface(titulo = "Data da Publicação", padrao = true)
    @Column(name = "DATA_PUBLICACAO", nullable = false)
    private LocalDateTime dataPublicacao;

    @RelatorioInterface(titulo = "Data da Disponibilização", padrao = true)
    @Column(name = "DATA_DISPONIBILIZACAO")
    private LocalDateTime dataDisponibilizacao;

    @JsonIgnore
    @Deprecated
    @RelatorioInterface(ignore = true)
    @Column(name = "DATAPUBLICACAO")
    private String dataPublicacaoOld;

    @Column(name = "ORIGEM")
    private String origem;

    @RelatorioInterface(ignore = true)
    @Column(name = "ORIGEMINTEGRACAO")
    private EnumOrigemIntegracaoWs origemIntegracao;

    @RelatorioInterface(ignore = true)
    @Column(name = "IDPUBLICACAO", nullable = false, unique = true)
    private Long idPublicacaoIntegracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @Column(name = "SITUACAO")
    private String situacao;

    @RelatorioInterface(ignore = true)
    @Column(name = "CASEEXECUTIONID")
    private String caseExecutionId;

    @RelatorioInterface(ignore = true)
    @Column(name = "PEGOU")
    private Boolean pegou;

    @RelatorioInterface(ignore = true)
    @Column(name = "MOTIVO_DEVOLUCAO")
    private String motivoDevolucao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DADOSBASICOSTAREFA")
    private DadosBasicosTarefa dadosBasicosTarefa;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    @RelatorioInterface(ignore = true)
    private String idUnicoProcesso;

    @Transient
    @RelatorioInterface(ignore = true)
    private Long idProcesso;

    public Publicacao() {
    }

    @QueryProjection
    public Publicacao(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Publicacao(Long id, Processo processo, LocalDateTime dataColagem, String situacao, String caseExecutionId) {
        this.id = id;
        this.processo = processo;
        this.dataColagem = dataColagem;
        this.situacao = situacao;
        this.caseExecutionId = caseExecutionId;
    }

    @QueryProjection
    public Publicacao(Long id, Processo processo, LocalDateTime dataColagem, String situacao, String caseExecutionId, String origem, LocalDateTime dataPublicacao, LocalDateTime dataDisponibilizacao) {
        this.id = id;
        this.processo = processo;
        this.dataColagem = dataColagem;
        this.situacao = situacao;
        this.caseExecutionId = caseExecutionId;
        this.origem = origem;
        this.dataPublicacao = dataPublicacao;
        this.dataDisponibilizacao = dataDisponibilizacao;
    }

    @QueryProjection
    public Publicacao(Long id, Processo processo, LocalDateTime dataColagem, String situacao, String caseExecutionId, String origem, LocalDateTime dataPublicacao, LocalDateTime dataDisponibilizacao, DadosBasicosTarefa dadosBasicosTarefa) {
        this.id = id;
        this.processo = processo;
        this.dataColagem = dataColagem;
        this.situacao = situacao;
        this.caseExecutionId = caseExecutionId;
        this.origem = origem;
        this.dataPublicacao = dataPublicacao;
        this.dataDisponibilizacao = dataDisponibilizacao;
        this.dadosBasicosTarefa = dadosBasicosTarefa;
    }

    public Publicacao(Long idPublicacaoIntegracao, Processo processo) {
        this.idPublicacaoIntegracao = idPublicacaoIntegracao;
        this.processo = processo;
    }

    @Override
    public Long getId() {
        return id;
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
        Publicacao that = (Publicacao) o;
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
