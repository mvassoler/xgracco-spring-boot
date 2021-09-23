package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumImportacao;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author Jordano
 */
@Entity
@Table(name = "GEDPROFILE")
@SequenceGenerator(allocationSize = 1, name = "seqProfile", sequenceName = "SEQ_PROFILE")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Profile extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqProfile")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @Column(name = "NOMEPROFILE", length = 100, nullable = true)
    private String nomeProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa autor;

    @DateTimeFormat(pattern= Util.PATTERN_DATA_HORA_SEG)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATACRIACAO", updatable = false)
    private Calendar dataCriacao;

    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "FK_PROFILE", nullable = true)
    @NotAudited
    private List<Arquivo> arquivos;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROFILE", nullable = true)
    private Profile profile;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Profile> profiles;

    @Column(name = "ISFINCH")
    private Boolean isFinch;

    @Column(name = "PROFILEFINCH")
    private Long profileFinch;

    @Transient
    private EnumImportacao modelo;

    @Transient
    private Long idProcesso;

    @Transient
    private Long idProfile;

    @Transient
    private Long idAutor;

    @Transient
    private List<Profile> pastas;

    @Transient
    private String json;

    @Transient
    private String dataCriacaoAux;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private Boolean remover;

    public Profile() {
    }

    @QueryProjection
    public Profile(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Profile(Long id, String nomeProfile, Processo processo) {
        this.id = id;
        this.nomeProfile = nomeProfile;
        this.processo = processo;
    }

    @QueryProjection
    public Profile(Long idProcesso, Long idProfile) {
        if (idProfile != null) {
            this.profile = new Profile();
            this.profile.setId(idProfile);
        }
        if (idProcesso != null) {
            this.processo = new Processo();
            this.processo.setId(idProcesso);
        }
    }

    @QueryProjection
    public Profile(Long id, Processo processo) {
        this.id = id;
        this.processo = processo;
    }

    @QueryProjection
    public Profile(Long id, String nomeProfile, Calendar dataCriacao, Processo processo, Profile profile, Pessoa autor) {
        this.id = id;
        this.nomeProfile = nomeProfile;
        this.dataCriacao = dataCriacao;
        this.processo = processo;
        this.profile = profile;
        this.autor = autor;
    }

    @QueryProjection
    public Profile(Long id, List<Arquivo> arquivos, List<Profile> profiles, Boolean isFinch) {
        this.id = id;
        this.arquivos = arquivos;
        this.profiles = profiles;
        this.isFinch = isFinch;
    }

    @PrePersist
    private void prePersist(){
        dataCriacao = Calendar.getInstance();
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
        return this.id.toString();
    }

    public Boolean getRemover() {
        if(remover == null){
            remover = false;
        }
        return remover;
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
        Profile profile = (Profile) o;
        return Objects.equals(this.getId(), profile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
