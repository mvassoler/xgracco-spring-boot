package br.com.finchsolucoes.xgracco.domain.exception;

import org.apache.poi.ss.usermodel.Cell;

public class ImportacaoPlanilhaException extends Exception {

    private Cell cell;
    private String mensagem;

    public ImportacaoPlanilhaException() {
        super();
    }

    public ImportacaoPlanilhaException(Cell cell, String mensagem) {
        super(mensagem);
        this.cell = cell;
        this.mensagem = mensagem;
    }

    public Cell getCell() {
        return cell;
    }

    public String getMensagem() {
        return mensagem;
    }
}
