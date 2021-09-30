package br.com.finchsolucoes.xgracco.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Diretório de documentos
 *
 * @author Rodolpho Couto
 */
@Relation(collectionRelation = "diretorios")
@Data
@Builder
@AllArgsConstructor
public class ProcessoDiretorio implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private Usuario usuario;
    @JsonIgnore
    private ProcessoDiretorio diretorioPai;
    @JsonIgnore
    private Processo processo;
    private List<ProcessoDiretorio> diretorios;
    private List<ProcessoDocumento> documentos;

    public ProcessoDiretorio() {
    }

    public ProcessoDiretorio(Long id,
                             String nome,
                             Usuario usuario,
                             ProcessoDiretorio diretorioPai,
                             Processo processo) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.diretorioPai = diretorioPai;
        this.processo = processo;
    }

    public ProcessoDiretorio(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessoDiretorio that = (ProcessoDiretorio) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
