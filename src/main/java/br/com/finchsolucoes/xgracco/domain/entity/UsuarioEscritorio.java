package br.com.finchsolucoes.xgracco.domain.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author thiago.fogar
 * @since 4.0.2
 */
@Entity
@Relation(collectionRelation = "usuario-escritorio")
@IdClass(UsuarioEscritorio.UsuarioEscritorioPK.class)
@Table(name = "USUARIO_ESCRITORIO")
@Data
@Builder
public class UsuarioEscritorio implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USUARIO", referencedColumnName = "ID")
    private Usuario usuario;

    @Id
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ESCRITORIO", referencedColumnName = "ID")
    private Escritorio escritorio;

    @Column(name = "PRINCIPAL")
    private Boolean principal;

    public UsuarioEscritorio() {
    }

    @PrePersist
    @PreUpdate
    void prePersist() {
        if (this.principal == null) {
            this.principal = false;
        }
    }

    @QueryProjection
    public UsuarioEscritorio(Usuario usuario, Escritorio escritorio, Boolean principal) {
        this.usuario = usuario;
        this.escritorio = escritorio;
        this.principal = principal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEscritorio that = (UsuarioEscritorio) o;
        return Objects.equals(usuario, that.usuario) &&
                Objects.equals(escritorio, that.escritorio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, escritorio);
    }

    static class UsuarioEscritorioPK implements Serializable {
        private Long usuario;
        private Long escritorio;

        public Long getIdUsuario() {
            return usuario;
        }

        public void setIdUsuario(Long usuario) {
            this.usuario = usuario;
        }

        public Long getIdEscritorio() {
            return escritorio;
        }

        public void setIdEscritorio(Long escritorio) {
            this.escritorio = escritorio;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UsuarioEscritorioPK that = (UsuarioEscritorioPK) o;
            return Objects.equals(usuario, that.usuario) &&
                    Objects.equals(escritorio, that.escritorio);
        }

        @Override
        public int hashCode() {
            return Objects.hash(usuario, escritorio);
        }
    }
}
