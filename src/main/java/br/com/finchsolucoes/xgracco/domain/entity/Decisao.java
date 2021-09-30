package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumDecisaoPolo;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumDecisaoPoloConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumInstanciaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * @author rianmachado
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Relation(collectionRelation = "decisoes")
@Table(name = "DECISAO", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao"}, name = "descDecisao")})
@SequenceGenerator(allocationSize = 1, name = "seqDecisao", sequenceName = "SEQ_DECISAO")
@RelatorioInterface(titulo = "Decisões")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Decisao implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqDecisao")
    @Column(name = "ID")
    private Long id;

    //@NotBlank
    //@Size(max = 100)
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO")
    private String descricao;

    @XmlIDREF
    @ElementCollection(fetch = FetchType.LAZY, targetClass = EnumInstancia.class)
    @CollectionTable(name = "DECISAO_INSTANCIA", joinColumns = @JoinColumn(name = "DECISAO_ID"))
    @Column(name = "INSTANCIA_ID")
    @NotAudited
    @Convert(converter = EnumInstanciaConverter.class)
    private List<EnumInstancia> instancias;

    @Column(name = "FK_POLO_ATIVO")
    @Convert(converter = EnumDecisaoPoloConverter.class)
    private EnumDecisaoPolo decisaoPoloPassivo;

    @Column(name = "FK_POLO_PASSIVO")
    @Convert(converter = EnumDecisaoPoloConverter.class)
    private EnumDecisaoPolo decisaoPoloAtivo;

    @Column(name = "BLOQUEAR_REATIVACAO", columnDefinition = "false")
    private Boolean bloquearReativacao;

    @Column(name = "ENVIA_PUBLICACAO_GRACCO")
    private Boolean enviaPublicacaoGracco;

    @Column(name = "RECEBE_PUBLICACAO_GRACCO")
    private Boolean recebePublicacaoGracco;

    @Transient
    private LogAuditoria logAuditoria;

    public Decisao() {
    }

    public Decisao(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Decisao(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @QueryProjection
    public Decisao(Long id, String descricao, boolean bloquearReativacao) {
        this.id = id;
        this.descricao = descricao;
        this.bloquearReativacao = bloquearReativacao;
    }

    @QueryProjection
    public Decisao(Long id, String descricao, boolean bloquearReativacao, Boolean enviaPublicacaoGracco, Boolean recebePublicacaoGracco) {
        this.id = id;
        this.descricao = descricao;
        this.bloquearReativacao = bloquearReativacao;
        this.enviaPublicacaoGracco = enviaPublicacaoGracco;
        this.recebePublicacaoGracco = recebePublicacaoGracco;
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
    public String toString() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Decisao decisao = (Decisao) o;
        return Objects.equals(this.getId(), decisao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }


}
