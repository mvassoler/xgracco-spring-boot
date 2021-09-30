package br.com.finchsolucoes.xgracco.infra.ws.boomerang.response;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Classe para receber a resposta da integração com o fornecedor de publicações T_Legal
 *
 * @author Marcelo Aguiar
 */
@XmlRootElement(name = "NewDataSet")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class WebPubsTLegalResponse {

    @XmlElements(
            @XmlElement(name = "Table"))
    private List<WebPubsTLegalPublicacao> publicacoes;

    public List<WebPubsTLegalPublicacao> getPublicacoes() {
        return publicacoes;
    }

    public void setPublicacoes(List<WebPubsTLegalPublicacao> publicacoes) {
        this.publicacoes = publicacoes;
    }
}
