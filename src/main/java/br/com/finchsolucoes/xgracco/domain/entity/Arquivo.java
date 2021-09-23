package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author saraveronez
 */
@Entity
@Table(name = "ARQUIVO")
@SequenceGenerator(allocationSize = 1, name = "seqArquivo", sequenceName = "SEQ_ARQUIVO")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Arquivo extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqArquivo")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CAMINHO_DOCUMENTO")
    private String caminhoDocumento;

    @Column(name = "NOMEARQUIVO")
    private String nomeArquivo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_ANEXO", updatable = false)
    private Calendar dataAnexo;

    @Column(name = "ANEXO_EMAIL")
    private boolean anexoEmail;

    @Column(name = "ENVIADO_INDEX")
    private boolean enviadoIndex;

    @Column(name = "DISPOSITIVO_VIRTUAL")
    private boolean dispositivoVirtual;

    @Column(name = "ID_ORIGEM")
    private int idSistemaOrigem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TIPODOCUMENTO")
    private TipoDocumento tipoDocumento;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROFILE", nullable = true)
    private Profile profile;

    @Column(name = "IDARQUIVOFINCH")
    private Long idArquivoFinch;

    @Column(name = "IDARQUIVO_WSINTEGRACAO")
    private String idArquivoWsIntegracao;

    @NotAudited
    @OneToMany(mappedBy = "arquivo")
    private List<SolicitacaoArquivo> solicitacoesArquivos;

    @Transient
    private Long idTipoDocumento;

    @Transient
    private String dataAnexoAux;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private Boolean remover;

    @Transient
    private Boolean finch;

    public Arquivo() {
    }

    public Arquivo(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Arquivo(Long id, String nomeArquivo, String caminhoDocumento, TipoDocumento tipoDocumento, Profile profile) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
        this.caminhoDocumento = caminhoDocumento;
        this.tipoDocumento = tipoDocumento;
        this.profile = profile;
    }

    @QueryProjection
    public Arquivo(Long id, String nomeArquivo, String caminhoDocumento, TipoDocumento tipoDocumento, Profile profile, Calendar dataAnexo) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
        this.caminhoDocumento = caminhoDocumento;
        this.tipoDocumento = tipoDocumento;
        this.profile = profile;
        this.dataAnexo = dataAnexo;
    }

    @QueryProjection
    public Arquivo(Long id, String caminhoDocumento, String nomeArquivo, Calendar dataAnexo, boolean anexoEmail, boolean enviadoIndex, boolean dispositivoVirtual, int idSistemaOrigem, TipoDocumento tipoDocumento, Profile profile, Long idArquivoFinch) {
        this.id = id;
        this.caminhoDocumento = caminhoDocumento;
        this.nomeArquivo = nomeArquivo;
        this.dataAnexo = dataAnexo;
        this.anexoEmail = anexoEmail;
        this.enviadoIndex = enviadoIndex;
        this.dispositivoVirtual = dispositivoVirtual;
        this.idSistemaOrigem = idSistemaOrigem;
        this.tipoDocumento = tipoDocumento;
        this.profile = profile;
        this.idArquivoFinch = idArquivoFinch;
    }

    @PrePersist
    private void prePersist(){
        dataAnexo = Calendar.getInstance();
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getCaminhoDocumentoEncoded() {
        if (caminhoDocumento == null || !caminhoDocumento.contains("/")) {
            return null;
        }

        try {
            return caminhoDocumento.substring(0, caminhoDocumento.lastIndexOf("/") + 1) + URLEncoder.encode(caminhoDocumento.substring(caminhoDocumento.lastIndexOf("/") + 1), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return caminhoDocumento;
        }
    }

    public String getProcessoNumero() {
        return (this.profile != null && this.profile.getProcesso() != null ? this.profile.getProcesso().getNumero() : null);
    }

    @Override
    public String getTextoLog() {
        return nomeArquivo;
    }


    public String getDataAnexoAux() {
        if (dataAnexoAux != null) {
            return dataAnexoAux;
        } else if (dataAnexo != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(Util.PATTERN_DATA_HORA_SEG);
            return sdf.format(dataAnexo.getTime());
        }

        return null;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    public Boolean getRemover() {
        if(remover == null){
            remover = false;
        }
        return remover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Arquivo arquivo = (Arquivo) o;
        return Objects.equals(this.getId(), arquivo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
