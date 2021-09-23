package br.com.finchsolucoes.xgracco.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Arquivo de processo
 *
 * @author Rodolpho Couto
 */
@Data
@Builder
@AllArgsConstructor
public class ProcessoDocumentoArquivo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private ProcessoDocumento documento;
    private byte[] content;
    private String contentType;

    public ProcessoDocumentoArquivo() {
    }

    public ProcessoDocumentoArquivo(ProcessoDocumento documento, byte[] content, String contentType) {
        this.documento = documento;
        this.content = content;
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessoDocumentoArquivo that = (ProcessoDocumentoArquivo) o;
        return Objects.equals(documento, that.documento) &&
                Arrays.equals(content, that.content) &&
                Objects.equals(contentType, that.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documento, content, contentType);
    }
}
