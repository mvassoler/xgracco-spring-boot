package br.com.finchsolucoes.xgracco.domain.dto.output;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "RETORNO_METODO_DTO", description = "Representação de uma resposta genéric1")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@Builder
@AllArgsConstructor
public class RetornoMetodoDTO implements Serializable, Identificavel<Long> {

    @ApiModelProperty(value = "sucesso")
    private boolean sucesso;

    @ApiModelProperty(value = "mensagem")
    private String mensagem;

    @ApiModelProperty(value = "pagina")
    private String pagina;

    @ApiModelProperty(value = "classe")
    private String classe;

    @ApiModelProperty(value = "id_gerado")
    private Long idGerado;

    @ApiModelProperty(value = "valor")
    private String valor;

    @ApiModelProperty(value = "exception")
    private String exception;

    @ApiModelProperty(value = "objeto")
    private Object objeto;

    @ApiModelProperty(value = "objetos")
    private List<Object> objetos;

    @ApiModelProperty(value = "chave_valor")
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
