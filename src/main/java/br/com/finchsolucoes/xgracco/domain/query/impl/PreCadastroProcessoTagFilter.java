package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroProcessoTag;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class PreCadastroProcessoTagFilter implements Filter<PreCadastroProcessoTag> {

    private Long id;
    private Long idPreCadastroProcesso;
    private Long idTag;

    public PreCadastroProcessoTagFilter() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPreCadastroProcesso() {
        return idPreCadastroProcesso;
    }

    public void setIdPreCadastroProcesso(Long idPreCadastroProcesso) {
        this.idPreCadastroProcesso = idPreCadastroProcesso;
    }

    public Long getIdTag() {
        return idTag;
    }

    public void setIdTag(Long idTag) {
        this.idTag = idTag;
    }
}
