package br.com.finchsolucoes.xgracco.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Relation(collectionRelation = "processos-usuarios")
@Table(name = "PROCESSO_USUARIO")
@Data
@Builder
public class ProcessoUsuario implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROCESSO_ID", referencedColumnName = "ID")
    private Processo processo;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID")
    private Usuario usuario;

    public ProcessoUsuario(Processo processo, Usuario usuario) {
        this.processo = processo;
        this.usuario = usuario;
    }

    public ProcessoUsuario() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessoUsuario that = (ProcessoUsuario) o;
        return Objects.equals(processo, that.processo) &&
                Objects.equals(usuario, that.usuario);
    }

    @Override
    public int hashCode() {

        return Objects.hash(processo, usuario);
    }
}
