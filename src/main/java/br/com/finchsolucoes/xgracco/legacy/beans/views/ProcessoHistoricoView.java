package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.ViewInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Marcelo Aguiar
 */
public class ProcessoHistoricoView {

    public ProcessoHistoricoView(Long id, Calendar dataAlteracao, String usuario, String processoUnico, String informacao, String antes, String depois) {
        this.id = id;
        this.dataAlteracao = dataAlteracao;
        this.usuario = usuario;
        this.processoUnico = processoUnico;
        this.informacao = informacao;
        this.antes = antes;
        this.depois = depois;
    }
    
    private SimpleDateFormat formatoDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    @ViewInterface(campo = "p.id", ordem = 0)
    private Long id;

    @ViewInterface(campo = "p.dataAlteracao", ordem = 1)
    private Calendar dataAlteracao;

    @ViewInterface(campo = "p.usuario.nomeRazaoSocial", ordem = 2)
    private String usuario;

    @ViewInterface(campo = "p.processo.processoUnico", ordem = 3)
    private String processoUnico;

    @ViewInterface(campo = "p.informacao", ordem = 4)
    private String informacao;

    @ViewInterface(campo = "p.antes", ordem = 5)
    private String antes;

    @ViewInterface(campo = "p.depois", ordem = 6)
    private String depois;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    // ALTERADO METODO GET PARA RETORNAR A DATA FORMATADA NA TELA
    // MARCELO AGUIAR -> 04/01/2016
    public String getDataAlteracao() {
        if(dataAlteracao != null){
            return formatoDataHora.format(dataAlteracao.getTime());
        } else{
            return "";
        }
    }

    public void setDataAlteracao(Calendar dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getProcessoUnico() {
        return processoUnico;
    }

    public void setProcessoUnico(String processoUnico) {
        this.processoUnico = processoUnico;
    }

    public String getInformacao() {
        return informacao;
    }

    public void setInformacao(String informacao) {
        this.informacao = informacao;
    }

    public String getAntes() {
        return antes;
    }

    public void setAntes(String antes) {
        this.antes = antes;
    }

    public String getDepois() {
        return depois;
    }

    public void setDepois(String depois) {
        this.depois = depois;
    }
}
