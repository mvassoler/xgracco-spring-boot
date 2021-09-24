package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroParte;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class PreCadastroParteFilter implements Filter<PreCadastroParte> {

    private Long id;
    private Long idPessoa;
    private Long idTipoParte;
    private Long idPreCadastroProcesso;

    public PreCadastroParteFilter() {}

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

    public Long getIdTipoParte() {
        return idTipoParte;
    }

    public void setIdTipoParte(Long idTipoParte) {
        this.idTipoParte = idTipoParte;
    }

    public Long getIdPreCadastroProcesso() {
        return idPreCadastroProcesso;
    }

    public void setIdPreCadastroProcesso(Long idPreCadastroProcesso) {
        this.idPreCadastroProcesso = idPreCadastroProcesso;
    }
}
