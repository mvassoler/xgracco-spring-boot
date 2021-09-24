package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Escritorio;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.util.Set;

/**
 * Filtro da entidade Escritorio
 * <p>
 * Created by jordano on 13/01/17.
 */
public class EscritorioFilter implements Filter<Escritorio> {

    private Long id;
    private final String cpfCnpj;
    private final String nomeRazaoSocial;
    private final String apelidoFantasia;
    private final Boolean usuario;
    private Set<Escritorio> escritorios;
    private final Boolean ativo;

    public EscritorioFilter(String cpfCnpj, String nomeRazaoSocial, String apelidoFantasia, Boolean usuario, Boolean ativo) {
        this(null, cpfCnpj, nomeRazaoSocial, apelidoFantasia, usuario, null, ativo);
    }

    public EscritorioFilter(Long id, String cpfCnpj, String nomeRazaoSocial, String apelidoFantasia, Boolean usuario, Set<Escritorio> escritorios, Boolean ativo) {
        this.id = id;
        this.cpfCnpj = cpfCnpj;
        this.nomeRazaoSocial = nomeRazaoSocial;
        this.apelidoFantasia = apelidoFantasia;
        this.usuario = usuario;
        this.escritorios = escritorios;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getNomeRazaoSocial() {
        return nomeRazaoSocial;
    }

    public String getApelidoFantasia() {
        return apelidoFantasia;
    }

    public Boolean getUsuario() {
        return usuario;
    }

    public Set<Escritorio> getEscritorios() {
        return escritorios;
    }

    public void setEscritorios(Set<Escritorio> escritorios) {
        this.escritorios = escritorios;
    }

    public Boolean getAtivo() {
        return ativo;
    }
}
