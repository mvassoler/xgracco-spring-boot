package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.ViewInterface;

import java.io.Serializable;

public class PessoaReduzidaView implements Serializable {

    private static final long serialVersionUID = -2809172249927966710L;

    @ViewInterface(campo = "p.id", ordem = 0)
    private Long id;

//    @ViewInterface(campo = "p.login", ordem = 1)
    private String coluna1;

    @ViewInterface(campo = "p.nomeRazaoSocial", ordem = 1)
    private String coluna2;

    @ViewInterface(campo = "p.apelidoFantasia", ordem = 2)
    private String coluna3;

    public PessoaReduzidaView(Long id, String coluna2, String coluna3) {
        super();
        this.id = id;
//        this.coluna1 = coluna1;
        this.coluna2 = coluna2;
        this.coluna3 = coluna3;
    }

    public PessoaReduzidaView() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColuna1() {
        return coluna1;
    }

    public void setColuna1(String coluna1) {
        this.coluna1 = coluna1;
    }

    public String getColuna2() {
        return coluna2;
    }

    public void setColuna2(String coluna2) {
        this.coluna2 = coluna2;
    }

    public String getColuna3() {
        return coluna3;
    }

    public void setColuna3(String coluna3) {
        this.coluna3 = coluna3;
    }

}
