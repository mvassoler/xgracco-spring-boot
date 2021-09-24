package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.ImportacaoPlanilha;
import br.com.finchsolucoes.xgracco.domain.enums.EnumImportacao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusImportacaoPlanilha;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

/**
 * Filtro de banco.
 *
 * @author Alessandro, Henrique, Eloi
 */
public class ImportacaoPlanilhaFilter implements Filter<ImportacaoPlanilha> {

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataEncaminhamento;
    private Long idUsuario;
    private EnumImportacao modelo;
    private Set<EnumStatusImportacaoPlanilha> status;

    public ImportacaoPlanilhaFilter() {
    }

    public LocalDate getDataEncaminhamento() {
        return dataEncaminhamento;
    }

    public void setDataEncaminhamento(LocalDate dataEncaminhamento) {
        this.dataEncaminhamento = dataEncaminhamento;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public EnumImportacao getModelo() {
        return modelo;
    }

    public void setModelo(EnumImportacao modelo) {
        this.modelo = modelo;
    }

    public ImportacaoPlanilhaFilter(Set<EnumStatusImportacaoPlanilha> status) {
        this.status = status;
    }

    public Set<EnumStatusImportacaoPlanilha> getStatus() {
        return status;
    }

    public void setStatus(Set<EnumStatusImportacaoPlanilha> status) {
        this.status = status;
    }

}
