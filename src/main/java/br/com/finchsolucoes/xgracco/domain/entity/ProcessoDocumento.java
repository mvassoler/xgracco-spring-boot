package br.com.finchsolucoes.xgracco.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Objects;

/**
 * Documento
 *
 * @author Rodolpho Couto
 */
@Relation(collectionRelation = "documentos")
@Data
@Builder
@AllArgsConstructor
public class ProcessoDocumento implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private TipoDocumento tipoDocumento;
    private Long idArquivoFinch;
    @JsonIgnore
    private String url;
    @JsonIgnore
    private ProcessoDiretorio diretorio;

    public ProcessoDocumento() {
    }

    public ProcessoDocumento(String nome, TipoDocumento tipoDocumento, String url, ProcessoDiretorio diretorio) {
        this.nome = nome;
        this.tipoDocumento = tipoDocumento;
        this.url = url;
        this.diretorio = diretorio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessoDocumento that = (ProcessoDocumento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
