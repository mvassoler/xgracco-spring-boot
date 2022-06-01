package br.com.finchsolucoes.xgracco.domain.dto.output;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcelo Aguiar
 */
@Schema(name = "RETORNO_METODO_DTO", description = "Representação de uma resposta genéric1")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@Builder
@AllArgsConstructor
public class RetornoMetodoDTO implements Serializable, Identificavel<Long> {

    @Schema(name = "sucesso")
    private boolean sucesso;

    @Schema(name = "mensagem")
    private String mensagem;

    @Schema(name = "pagina")
    private String pagina;

    @Schema(name = "classe")
    private String classe;

    @Schema(name = "id_gerado")
    private Long idGerado;

    @Schema(name = "valor")
    private String valor;

    @Schema(name = "exception")
    private String exception;

    @Schema(name = "objeto")
    private Object objeto;

    @Schema(name = "objetos")
    private List<Object> objetos;

    @Schema(name = "chave_valor")
    private Map<String, Object> chaveValor;

    public RetornoMetodoDTO() {
        objetos = new ArrayList<>();
        chaveValor = new HashMap<>();
    }

    public RetornoMetodoDTO(boolean sucesso, String mensagem) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
    }

    public RetornoMetodoDTO(boolean sucesso, String mensagem, String exception) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.exception = exception;
    }

    @Override
    public Long getPK() {
        return idGerado;
    }

    @Override
    public String getTextoLog() {
        return "Sucesso: " + sucesso + " | " + mensagem;
    }
}
