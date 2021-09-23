package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.ViewInterface;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by marceloaguiar on 13/05/16.
 */
@Data
@Builder
public class ContratoView implements Serializable {

    public ContratoView(Long id, String nome, String numero, String cliente) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
        this.cliente = cliente;
    }

    @ViewInterface(campo = "c.id", ordem = 0)
    private Long id;

    @ViewInterface(campo = "c.nome", ordem = 1)
    private String nome;

    @ViewInterface(campo = "c.numero", ordem = 2)
    private String numero;

    @ViewInterface(campo = "c.cliente.nomeRazaoSocial", ordem = 3)
    private String cliente;

}
