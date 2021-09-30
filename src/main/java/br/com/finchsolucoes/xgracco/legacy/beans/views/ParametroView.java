package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.ViewInterface;

import java.io.Serializable;

public class ParametroView implements Serializable, Identificavel<Long> {

    private static final long serialVersionUID = -8366943545995049896L;

    @ViewInterface(campo = "p.id", ordem = 0)
    private Long id;

    @ViewInterface(campo = "p.chave", ordem = 1)
    private EnumParametro chave;

    @ViewInterface(campo = "p.descricao", ordem = 2)
    private String descricao;

    @ViewInterface(campo = "p.valor", ordem = 3)
    private String valor;

    @ViewInterface(campo = "p.classe", ordem = 4)
    private String classe;

    private Long idPessoa;

    private Long idParametroEspecifico;

    private String valorEspecifico;

    public ParametroView() {
    }

    public ParametroView(Long id, EnumParametro chave, String descricao, String valor, String classe) {
        super();
        this.id = id;
        this.chave = chave;
        this.descricao = descricao;
        this.valor = valor;
        this.classe = classe;
    }

    public String getChave() {
        if (chave != null) {
            return chave.getId();
        }
        return null;
    }

    public EnumParametro getChaveEnum() {
        return chave;
    }

    public void setChave(EnumParametro chave) {
        this.chave = chave;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getClasse() {
        if (classe != null) {
            return classe.replace(".", " ");
        }
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Long getIdParametroEspecifico() {
        return idParametroEspecifico;
    }

    public void setIdParametroEspecifico(Long idParametroEspecifico) {
        this.idParametroEspecifico = idParametroEspecifico;
    }

    public String getValorEspecifico() {
        return valorEspecifico;
    }

    public void setValorEspecifico(String valorEspecifico) {
        this.valorEspecifico = valorEspecifico;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return id + descricao;
    }

}
