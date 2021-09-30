package br.com.finchsolucoes.xgracco.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Arquivo Dados Basicos Tarefa Campos
 *
 * @author thiago.fogar
 */
@Data
@Builder
@AllArgsConstructor
public class DadosBasicosTarefaArquivo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private DadosBasicosTarefaCampos dadosBasicosTarefaCampos;
    private byte[] content;
    private String contentType;

    public DadosBasicosTarefaArquivo() {
    }

    public DadosBasicosTarefaArquivo(DadosBasicosTarefaCampos dadosBasicosTarefaCampos, byte[] content, String contentType) {
        this.dadosBasicosTarefaCampos = dadosBasicosTarefaCampos;
        this.content = content;
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DadosBasicosTarefaArquivo that = (DadosBasicosTarefaArquivo) o;
        return Objects.equals(dadosBasicosTarefaCampos, that.dadosBasicosTarefaCampos) &&
                Arrays.equals(content, that.content) &&
                Objects.equals(contentType, that.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dadosBasicosTarefaCampos, content, contentType);
    }
}
