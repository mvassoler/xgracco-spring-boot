package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.PessoaDiretorio;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaDocumento;
import br.com.finchsolucoes.xgracco.domain.entity.TipoDocumento;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.util.Set;

/**
 * @author guilhermecamargo
 */
public class PessoaDocumentoFilter implements Filter<PessoaDocumento> {

    private String nomeArquivo;
    private String caminhoDocumento;
    private Set<TipoDocumento> tiposDocumento;
    private Set<PessoaDiretorio> diretorios;

    public PessoaDocumentoFilter(String nomeArquivo, String caminhoDocumento, Set<TipoDocumento> tiposDocumento, Set<PessoaDiretorio> diretorios) {
        this.nomeArquivo = nomeArquivo;
        this.caminhoDocumento = caminhoDocumento;
        this.tiposDocumento = tiposDocumento;
        this.diretorios = diretorios;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public String getCaminhoDocumento() {
        return caminhoDocumento;
    }

    public Set<TipoDocumento> getTiposDocumento() {
        return tiposDocumento;
    }

    public Set<PessoaDiretorio> getDiretorios() {
        return diretorios;
    }
}
