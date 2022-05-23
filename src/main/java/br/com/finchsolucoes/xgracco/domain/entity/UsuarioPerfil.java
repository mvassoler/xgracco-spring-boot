package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.querydsl.core.annotations.QueryProjection;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

@Entity
@Relation(collectionRelation = "usuario-perfil")
@SequenceGenerator(allocationSize = 1, name = "seqUsuarioPerfil", sequenceName = "SEQ_USUARIOPERFIL")
@Table(name = "USUARIO_PERFIL")
@Audited
public class UsuarioPerfil implements Serializable, EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqUsuarioPerfil")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USUARIO", referencedColumnName = "ID")
    @JsonBackReference
    private Usuario usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PERFIL", referencedColumnName = "ID")
    private Perfil perfil;

    @Transient
    private LogAuditoria logAuditoria;


    public UsuarioPerfil() {
    }

    public UsuarioPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @QueryProjection
    public UsuarioPerfil(Long id, Usuario usuario, Perfil perfil) {
        this.id = id;
        this.usuario = usuario;
        this.perfil = perfil;
    }

    @QueryProjection
    public UsuarioPerfil(Usuario usuario, Perfil perfil) {
        this.usuario = usuario;
        this.perfil = perfil;
    }

    public UsuarioPerfil(Long idUsuarioPerfil) {
        this.id = idUsuarioPerfil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    public Perfil getPerfil() {
        return perfil;
    }


    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioPerfil that = (UsuarioPerfil) o;
        return Objects.equals(usuario, that.usuario) &&
                Objects.equals(perfil, that.perfil);
    }

    @Override
    public int hashCode() {

        return Objects.hash(usuario, perfil);
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }


    static class UsuarioPerfilPK implements Serializable {
        private Long usuario;
        private Long perfil;

        public Long getIdUsuario() {
            return usuario;
        }

        public void setIdUsuario(Long usuario) {
            this.usuario = usuario;
        }

        public Long getIdEscritorio() {
            return perfil;
        }

        public void setIdEscritorio(Long escritorio) {
            this.perfil = escritorio;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UsuarioPerfilPK that = (UsuarioPerfilPK) o;
            return Objects.equals(usuario, that.usuario) &&
                    Objects.equals(perfil, that.perfil);
        }

        @Override
        public int hashCode() {
            return Objects.hash(usuario, perfil);
        }
    }
}