package br.com.finchsolucoes.xgracco.core.handler.exception;

import org.apache.poi.ss.usermodel.Cell;

public class ImportacaoPlanilhaException extends Exception {

    public ImportacaoPlanilhaException(Cell cell) {
        this.cell = cell;
    }

    public ImportacaoPlanilhaException(String message, Cell cell) {
        super(message);
        this.cell = cell;
    }

    public ImportacaoPlanilhaException(String message, Throwable cause, Cell cell) {
        super(message, cause);
        this.cell = cell;
    }

    public ImportacaoPlanilhaException(Throwable cause, Cell cell) {
        super(cause);
        this.cell = cell;
    }

    public ImportacaoPlanilhaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Cell cell) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.cell = cell;
    }

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
