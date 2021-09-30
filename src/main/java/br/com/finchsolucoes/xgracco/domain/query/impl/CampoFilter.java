package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Campo;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoCampo;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Filtro de campo.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class CampoFilter implements Filter<Campo> {

    private String descricao;
    private List<EnumTipoCampo> tipos;
    private boolean ativo;
    private Campo campoAtual;
    private Set<Long> formularioId;
    private Set<Long> grupoCampoId;

    public CampoFilter(){};

    private CampoFilter(String descricao, List<EnumTipoCampo> tipos) {
        this.descricao = descricao;
        this.tipos = tipos;
        this.setFormularioId(new HashSet<>());
        this.setGrupoCampoId(new HashSet<>());
    }

    public CampoFilter(String descricao, List<EnumTipoCampo> tipos, boolean ativo, Campo campoAtual, Set<Long> formularioId, Set<Long> grupoCampoId) {
        this(descricao, tipos);
        this.setAtivo(ativo);
        this.setCampoAtual(campoAtual);
        this.setFormularioId(formularioId);
        this.setGrupoCampoId(grupoCampoId);
    }

    public String getDescricao() {
        return descricao;
    }

    public List<EnumTipoCampo> getTipos() {
        return tipos;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Campo getCampoAtual() {
        return campoAtual;
    }

    public void setCampoAtual(Campo campoAtual) {
        this.campoAtual = campoAtual;
    }

    public Set<Long> getFormularioId() {
        return formularioId;
    }

    public void setFormularioId(Set<Long> formularioId) {
        this.formularioId = formularioId;
    }

    public Set<Long> getGrupoCampoId() {
        return grupoCampoId;
    }

    public void setGrupoCampoId(Set<Long> grupoCampoId) {
        this.grupoCampoId = grupoCampoId;
    }

}
