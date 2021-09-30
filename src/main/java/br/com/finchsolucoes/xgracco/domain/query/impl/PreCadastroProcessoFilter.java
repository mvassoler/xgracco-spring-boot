package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroProcesso;
import br.com.finchsolucoes.xgracco.domain.enums.EnumProcessoEncerramento;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class PreCadastroProcessoFilter implements Filter<PreCadastroProcesso> {

    private Long id;
    private Long idComarca;
    private String uf;
    private String numeroProcesso;
    private String cpfCnpjParteInteressada;
    private String cpfCnpjParteContraria;
    private String cpfCnpjProcessoParte;
    private Long idParteInteressada;
    private String processoUnico;
    private Long idParteContraria;
    private Long idProcesso;
    private EnumProcessoEncerramento status;

    public PreCadastroProcessoFilter() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdComarca() {
        return idComarca;
    }

    public void setIdComarca(Long idComarca) {
        this.idComarca = idComarca;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public String getCpfCnpjParteInteressada() {
        return cpfCnpjParteInteressada;
    }

    public void setCpfCnpjParteInteressada(String cpfCnpjParteInteressada) {
        this.cpfCnpjParteInteressada = cpfCnpjParteInteressada;
    }

    public String getCpfCnpjParteContraria() {
        return cpfCnpjParteContraria;
    }

    public void setCpfCnpjParteContraria(String cpfCnpjParteContraria) {
        this.cpfCnpjParteContraria = cpfCnpjParteContraria;
    }

    public String getCpfCnpjProcessoParte() {
        return cpfCnpjProcessoParte;
    }

    public void setCpfCnpjProcessoParte(String cpfCnpjProcessoParte) {
        this.cpfCnpjProcessoParte = cpfCnpjProcessoParte;
    }

    public Long getIdParteInteressada() {
        return idParteInteressada;
    }

    public void setIdParteInteressada(Long idParteInteressada) {
        this.idParteInteressada = idParteInteressada;
    }

    public String getProcessoUnico() {
        return processoUnico;
    }

    public void setProcessoUnico(String processoUnico) {
        this.processoUnico = processoUnico;
    }

    public Long getIdParteContraria() {
        return idParteContraria;
    }

    public void setIdParteContraria(Long idParteContraria) {
        this.idParteContraria = idParteContraria;
    }

    public Long getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Long idProcesso) {
        this.idProcesso = idProcesso;
    }

    public EnumProcessoEncerramento getStatus() {
        return status;
    }

    public void setStatus(EnumProcessoEncerramento status) {
        this.status = status;
    }
}
