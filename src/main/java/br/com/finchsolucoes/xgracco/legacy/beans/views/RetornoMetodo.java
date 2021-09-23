package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
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
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@Builder
@AllArgsConstructor
public class RetornoMetodo implements Serializable, Identificavel<Long> {

    private boolean sucesso;
    private String mensagem;
    private String pagina;
    private String classe;
    private Long idGerado;
    private String valor;
    private String exception;
    private Object objeto;
    private List<Object> objetos;
    private Map<String, Object> chaveValor;

    public RetornoMetodo() {
        objetos = new ArrayList<>();
        chaveValor = new HashMap<>();
    }

    public RetornoMetodo(boolean sucesso, String mensagem) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
    }

    public RetornoMetodo(boolean sucesso, String mensagem, String exception) {
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
