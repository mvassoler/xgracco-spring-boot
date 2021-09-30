package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusSolicitacao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoProcesso;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumStatusSolicitacaoConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author saraveronez
 */
@Entity
@Table(name = "SOLICITACAO_LOG")
@SequenceGenerator(allocationSize = 1, name = "seqSolicitacaoLog", sequenceName = "SEQ_SOLICITACAO_LOG")
@XmlRootElement(name = "tem:lobjSolicitacao")
@Audited
@Data
@Builder
@AllArgsConstructor
public class SolicitacaoLog extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqSolicitacaoLog")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JoinColumn(name = "FK_SOLICITANTE")
    private Pessoa solicitante;

    @Column(name = "PROCESSOUNICO")
    private String processoUnicoWsIntegracao;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy", iso = DateTimeFormat.ISO.DATE)
    @Column(name = "DATALIMITE")
    private Calendar dataLimite;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATAAUDIENCIA")
    private Calendar dataAudiencia;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATASOLICITACAO")
    private Calendar data;

    @Column(name = "HORAAUDIENCIA")
    private String horaAudiencia;

    @Column(name = "LOCAL")
    private String local;

    @Column(name = "PREPOSTO")
    private Integer preposto;

    @Column(name = "NOMEPREPOSTO")
    private String nomePreposto;

    @NotNull
    @Column(name = "IDCOMARCA")
    private Integer idComarca;

    @Column(name = "TEXTOSOLICITACAO")
    private String textoSolicitacao;

    @Column(name = "IDSOLICITACAOBOOMER")
    private Long idSolicitacaoBoomer;

    @NotNull
    @Column(name = "IDTIPODILIGENCIA")
    private Integer idTipoDiligencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDPROCESSO")
    private Processo processo;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Column(name = "ID_STATUS_SOLICITACAO")
    @Convert(converter = EnumStatusSolicitacaoConverter.class)
    private EnumStatusSolicitacao statusSolicitacao;

    @Column(name = "IDPROCESSO", insertable = false, updatable = false)
    private Long idProcesso;

    @Column(name = "MENSAGEM")
    private String mensagem;

    @Column(name = "DESCRICAO_TIPO")
    private String descTipoSolicitacao;

    @Column(name = "DESC_CORRESPONDENTE")
    private String descCorrespondente;

    @Column(name = "NUMERO_VARA")
    private String numeroVara;

    @Column(name = "DESC_VARA")
    private String descVara;

    @Column(name = "DESC_REPARTICAO")
    private String descReparticao;

    @Column(name = "DESC_JUSTICA")
    private String descJustica;

    @Column(name = "DESC_FORO")
    private String descForo;

    @Column(name = "DESC_SISTEMAVIRTUAL")
    private String descSistemaVirtual;

    @Transient
    private String status;

    @Transient
    @Temporal(TemporalType.DATE)
    private Calendar dataSolicitacao;

    @Transient
    private String tipoSolicitacao;

    @Transient
    private String json;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private Integer idCorrespondente;

    @Transient
    private Long idUf;

    @Transient
    private String usuarioSolicitante;

    @Transient
    private String comarcaDesc;

    @Transient
    private String ufSigla;

    @Transient
    private String jurisdicao;

    @Transient
    private Integer idSistemaVirtual;

    public SolicitacaoLog() {
    }

    @QueryProjection
    public SolicitacaoLog(Long id) {
        this.id = id;
    }

    @QueryProjection
    public SolicitacaoLog(Long id, Calendar dataSolicitacao, Calendar dataLimite, Calendar dataAudiencia, String horaAudiencia, Long idProcesso, String local, String textoSolicitacao, Long idSolicitacaoBoomer, String descTipoSolicitacao, EnumStatusSolicitacao statusSolicitacao, Processo processo, Pessoa solicitante) {
        this.id = id;
        this.dataLimite = dataLimite;
        this.data = dataSolicitacao;
        this.dataAudiencia = dataAudiencia;
        this.horaAudiencia = horaAudiencia;
        this.idProcesso = idProcesso;
        this.local = local;
        this.descTipoSolicitacao = descTipoSolicitacao;
        this.textoSolicitacao = textoSolicitacao;
        this.idSolicitacaoBoomer = idSolicitacaoBoomer;
        this.statusSolicitacao = statusSolicitacao;
        this.processo = processo;
        this.solicitante = solicitante;
    }


    @QueryProjection
    public SolicitacaoLog(Long id, Calendar dataLimite, Calendar dataAudiencia, String horaAudiencia, Long idProcesso, String local, String mensagem, String textoSolicitacao, Long idSolicitacaoBoomer, String descCorrespondente) {
        this.id = id;
        this.dataLimite = dataLimite;
        this.dataAudiencia = dataAudiencia;
        this.horaAudiencia = horaAudiencia;
        this.idProcesso = idProcesso;
        this.local = local;
        this.mensagem = mensagem;
        this.textoSolicitacao = textoSolicitacao;
        this.idSolicitacaoBoomer = idSolicitacaoBoomer;
        this.descCorrespondente = descCorrespondente;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Long getPK() {
        return this.id;
    }

    @Override
    public String getTextoLog() {
        return tipoSolicitacao;
    }

    @XmlTransient
    public Pessoa getSolicitante() {
        return solicitante;
    }



    @XmlTransient
    public Long getIdSolicitacaoBoomer() {
        return idSolicitacaoBoomer;
    }

    public void setIdSolicitacaoBoomer(Long idSolicitacaoBoomer) {
        this.idSolicitacaoBoomer = idSolicitacaoBoomer;
    }

    @XmlElement(name = "tem:Local")
    public String getLocal() {
        return local;
    }



    @XmlElement(name = "tem:IDProcessoUnico")
    public String getProcessoUnicoWsIntegracao() {
        return processoUnicoWsIntegracao;
    }



    @XmlElement(name = "tem:TextoSolicitacao")
    public String getTextoSolicitacao() {
        return textoSolicitacao;
    }



    @XmlElement(name = "tem:DataLimite")
    public Calendar getDataLimite() {
        return dataLimite;
    }



    @XmlElement(name = "tem:DataAudiencia")
    public Calendar getDataAudiencia() {
        return dataAudiencia;
    }



    @XmlElement(name = "tem:HoraAudienca")
    public String getHoraAudiencia() {
        return horaAudiencia;
    }



    @XmlElement(name = "tem:Preposto")
    public Integer getPreposto() {
        return preposto;
    }



    @XmlElement(name = "tem:NomePreposto")
    public String getNomePreposto() {
        return nomePreposto;
    }



    @XmlElement(name = "tem:IdComarca")
    public Integer getIdComarca() {
        return idComarca;
    }



    @XmlElement(name = "tem:IDTipoSolicitacao")
    public Integer getIdTipoDiligencia() {
        return idTipoDiligencia;
    }



    @XmlTransient
    public Long getIdProcesso() {
        return idProcesso;
    }



    @XmlElement(name="tem:IDCorrespondente")
    public Integer getIdCorrespondente() { return idCorrespondente; }



    @XmlElement(name="tem:IdSistema")
    public Integer getIdSistemaVirtual() {
        return idSistemaVirtual;
    }



    @XmlTransient
    public String getDescSistemaVirtual() {
        return descSistemaVirtual;
    }


    @XmlTransient
    public String getStatus() {
        return status;
    }

    @XmlTransient
    public Calendar getDataSolicitacao() {
        return dataSolicitacao;
    }

    @XmlTransient
    public String getTipoSolicitacao() {
        return tipoSolicitacao;
    }

    @XmlTransient
    public String getMensagem() {
        return mensagem;
    }

    @XmlTransient
    public String getJson() {
        return json;
    }

    @XmlTransient
    public String getDescCorrespondente() {
        return descCorrespondente;
    }

    @XmlTransient
    public Processo getProcesso() {
        return processo;
    }

    @XmlElement(name = "tem:UsuarioSolicitante")
    public String getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    @XmlElement(name = "tem:Jurisdicao")
    public String getJurisdicao() {
        return jurisdicao;
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
        SolicitacaoLog that = (SolicitacaoLog) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    public String getJurisdicaoCompleta() {
        if (Objects.nonNull(getProcesso().getTipoProcesso())) {
            if (getProcesso().getTipoProcesso().equals(EnumTipoProcesso.ADMINISTRATIVO)) {
                return getDescReparticao() + " da comarca de " + getComarcaDesc() + " - " + getUfSigla();
            } else {
                if (Objects.nonNull(getProcesso().getArea())) {
                    if (getProcesso().getArea().equals(EnumArea.TRABALHISTA)) {
                        return getNumeroVara() + "ª " + getDescVara() +
                                " da comarca de " + getComarcaDesc() + "/" + getUfSigla();
                    } else { //DEMAIS ÁREAS
                        return getNumeroVara() + "ª " + getDescVara() +
                                " do " + getDescForo() +
                                " da comarca de " + getComarcaDesc() + "/" + getUfSigla();
                    }
                }
            }
        }
        return "";
    }


}
