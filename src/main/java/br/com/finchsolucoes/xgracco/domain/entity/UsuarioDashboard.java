package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.core.validation.Unique;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author felipe.costa
 * @since 5.17.0
 */
@Entity
@Unique("FK_USUARIO")
@SequenceGenerator(allocationSize = 1, name = "seqUsuarioDashboard", sequenceName = "SEQ_USUARIO_DASHBOARD")
@Table(name = "USUARIO_DASHBOARD")
@Data
@Builder
public class UsuarioDashboard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqUsuarioDashboard")
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USUARIO", referencedColumnName = "ID")
    private Usuario usuario;

    @Column(name = "BASE_ATIVA")
    private Boolean baseAtiva;

    public UsuarioDashboard() {
    }

    public UsuarioDashboard(Usuario usuario) {
        this.usuario = usuario;
        this.baseAtiva = baseAtiva;
    }

    @QueryProjection
    public UsuarioDashboard(Usuario usuario, Boolean baseAtiva) {
        this.usuario = usuario;
        this.baseAtiva = baseAtiva;
    }

    @QueryProjection
    public UsuarioDashboard(Long id, Usuario usuario, Boolean baseAtiva) {
        this.id = id;
        this.usuario = usuario;
        this.baseAtiva = baseAtiva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioDashboard that = (UsuarioDashboard) o;
        return Objects.equals(usuario, that.usuario) &&
                Objects.equals(baseAtiva, that.baseAtiva);
    }

    @Override
    public int hashCode() {

        return Objects.hash(usuario, baseAtiva);
    }

    @PrePersist
    @PreUpdate
    void prePersist() {
        if (this.baseAtiva == null) {
            this.baseAtiva = false;
        }
    }
}
