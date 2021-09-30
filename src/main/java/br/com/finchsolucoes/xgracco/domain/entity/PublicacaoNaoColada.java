package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusPublicacaoNaoColada;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumStatusPublicacaoNaoColadaConverter;
import br.com.finchsolucoes.xgracco.infra.ws.boomerang.response.WebPubsTLegalPublicacao;
import br.com.finchsolucoes.xgracco.infra.ws.enforce.PublicacaoEnforceResponse;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author guilhermecamargo
 */

@Entity
@Audited
@Table(name = "PUBLICACAO_NAO_COLADA")
@Relation(collectionRelation = "publicacoes-nao-coladas")
@SequenceGenerator(allocationSize = 1, name = "seqPubNaoColada", sequenceName = "SEQ_PUBNCOLADA")
@Data
@Builder
@AllArgsConstructor
public class PublicacaoNaoColada implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPubNaoColada")
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMPRESA",length = 250)
    private String empresa;

    @Column(name = "PARTES",length = 250)
    private String partes;

    @Column(name = "ORGAO",length = 250)
    private String orgao;

    @Column(name = "JUIZO")
    private String juizo;

    @Column(name = "NOME_DIARIO",length = 250)
    private String nomeDiario;

    @Column(name = "NUMERO_PROCESSO",length = 30)
    private String numeroProcesso;

    @Column(name = "TEXTO_PUBLICACAO")
    private String textoPublicacao;

    @Column(name = "CODIGO_RELACIONAL")
    private Long codigoRelacional;

    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;

    @Column(name = "DATA_ALTERACAO")
    private LocalDateTime dataCirculacao;

    @Column(name = "DATA_PUBLICACAO")
    private LocalDateTime dataPublicacao;

    @Column(name = "STATUS")
    @Convert(converter = EnumStatusPublicacaoNaoColadaConverter.class)
    private EnumStatusPublicacaoNaoColada status;

    @Column(name = "MOTIVO_TRATAMENTO", length = 255)
    private String motivoTratamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @Column(name  = "PLANO_ECONOMICO")
    private boolean planoEconomico;

    @Transient
    private LogAuditoria logAuditoria;

    public PublicacaoNaoColada(){

    }

    @QueryProjection
    public PublicacaoNaoColada(Long id) {
        this.id = id;
    }

    @QueryProjection
    public PublicacaoNaoColada(Long id, String empresa, String partes, String orgao, String juizo, String nomeDiario,
                               String numeroProcesso, String textoPublicacao, Long codigoRelacional, LocalDateTime dataCriacao,
                               LocalDateTime dataCirculacao, LocalDateTime dataPublicacao, EnumStatusPublicacaoNaoColada status, String motivoTratamento, Processo processo) {
        this.id = id;
        this.empresa = empresa;
        this.partes = partes;
        this.orgao = orgao;
        this.juizo = juizo;
        this.nomeDiario = nomeDiario;
        this.numeroProcesso = numeroProcesso;
        this.textoPublicacao = textoPublicacao;
        this.codigoRelacional = codigoRelacional;
        this.dataCriacao = dataCriacao;
        this.dataCirculacao = dataCirculacao;
        this.dataPublicacao = dataPublicacao;
        this.status = status;
        this.motivoTratamento = motivoTratamento;
        this.processo = processo;
    }

    public PublicacaoNaoColada(PublicacaoEnforceResponse pub) throws UnsupportedEncodingException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.empresa = Util.trataTextoPublicacao(pub.getEmpresa());
        this.partes = Util.trataTextoPublicacao(pub.getPartes());
        this.orgao = Util.trataTextoPublicacao(pub.getOrgao());
        this.juizo = Util.trataTextoPublicacao(pub.getJuizo());
        this.nomeDiario = Util.trataTextoPublicacao(pub.getDiario());
        this.numeroProcesso = pub.getProcesso();
        this.textoPublicacao = Util.trataTextoPublicacao(pub.getPublicacao());
        this.codigoRelacional = pub.getCodigoRelacional();
        this.dataCriacao = (StringUtils.isNotBlank(pub.getCreatedAt())) ? ZonedDateTime.parse(pub.getCreatedAt()).toLocalDateTime() : LocalDateTime.now();
        this.dataCirculacao = (StringUtils.isNotBlank(pub.getUpdated_at())) ? ZonedDateTime.parse(pub.getUpdated_at()).toLocalDateTime() : LocalDateTime.now();
        this.dataPublicacao = (StringUtils.isNotBlank(pub.getDataPublicacao())) ? ZonedDateTime.parse(pub.getUpdated_at()).toLocalDateTime() : LocalDateTime.now();
        this.status = EnumStatusPublicacaoNaoColada.PENDENTE;
    }

    public PublicacaoNaoColada(WebPubsTLegalPublicacao pub) throws UnsupportedEncodingException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.empresa = Util.trataTextoPublicacao(pub.getEmpresa());
        this.partes = Util.trataTextoPublicacao(pub.getPartes());
        this.orgao = Util.trataTextoPublicacao(pub.getOrgao());
        this.juizo = Util.trataTextoPublicacao(pub.getJuizo());
        this.nomeDiario = Util.trataTextoPublicacao(pub.getDiario());
        this.numeroProcesso = pub.getProcesso();
        this.textoPublicacao = Util.trataTextoPublicacao(pub.getPublicacao());
        this.codigoRelacional = pub.getCodigoRelacional();
        this.dataCriacao =  LocalDateTime.now();
        this.dataCirculacao = (pub.getDataCirculacao() != null) ? pub.getDataCirculacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : LocalDateTime.now();
        this.dataPublicacao = (pub.getDataPublicacao() != null ) ? pub.getDataPublicacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : LocalDateTime.now();
        this.status = EnumStatusPublicacaoNaoColada.PENDENTE;
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
        if (o == null || getClass() != o.getClass()) return false;
        PublicacaoNaoColada that = (PublicacaoNaoColada) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}