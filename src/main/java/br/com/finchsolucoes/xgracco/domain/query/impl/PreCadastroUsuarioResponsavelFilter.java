package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroUsuarioResponsavel;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.time.LocalDateTime;

public class PreCadastroUsuarioResponsavelFilter implements Filter<PreCadastroUsuarioResponsavel> {

    private Long id;
    private Long idPessoa;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Long idPreCadastroProcesso;

    public PreCadastroUsuarioResponsavelFilter() {}

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

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public Long getIdPreCadastroProcesso() {
        return idPreCadastroProcesso;
    }

    public void setIdPreCadastroProcesso(Long idPreCadastroProcesso) {
        this.idPreCadastroProcesso = idPreCadastroProcesso;
    }
}
