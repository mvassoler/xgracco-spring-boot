package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.PessoaEmpresaColigada;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro da entidade EmpresaColigada
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class PessoaEmpresaColigadaFilter implements Filter<PessoaEmpresaColigada> {

    private final String nomeRazaoSocial;
    private final String apelidoFantasia;
    private final String cpfCnpj;

    public PessoaEmpresaColigadaFilter(String nomeRazaoSocial, String apelidoFantasia, String cpfCnpj) {
        this.nomeRazaoSocial = nomeRazaoSocial;
        this.apelidoFantasia = apelidoFantasia;
        this.cpfCnpj = cpfCnpj;
    }

    public String getNomeRazaoSocial() {
        return nomeRazaoSocial;
    }

    public String getApelidoFantasia() {
        return apelidoFantasia;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }
}
