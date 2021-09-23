/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

/**
 * @author maiconcarraro
 */
@Data
@Builder
public class ColunaExportacao {

    private String nome;
    private String metodo;

    public ColunaExportacao() {
    }

    public ColunaExportacao(String nome, String metodo) {
        this.nome = nome;
        this.metodo = metodo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ColunaExportacao that = (ColunaExportacao) o;
        return Objects.equals(this.getNome(), that.getNome()) &&
                Objects.equals(this.getMetodo(), that.getMetodo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getNome(), this.getMetodo());
    }
}
