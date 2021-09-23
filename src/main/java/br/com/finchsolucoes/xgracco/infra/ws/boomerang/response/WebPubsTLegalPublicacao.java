package br.com.finchsolucoes.xgracco.infra.ws.boomerang.response;

import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Classe para receber a resposta da integração com o fornecedor de publicações T_Legal
 *
 * @author Marcelo Aguiar
 */
@XmlRootElement(name = "Table")
@XmlAccessorType(value = XmlAccessType.FIELD)
@Data
@Builder
public class WebPubsTLegalPublicacao {

    @XmlElement(name = "Empresa")
    private String empresa;

    @XmlElement(name = "Partes")
    private String partes;

    @XmlElement(name = "DataPublicacao")
    private Date dataPublicacao;

    @XmlElement(name = "Processo")
    private String processo;

    @XmlElement(name = "Diario")
    private String diario;

    @XmlElement(name = "Pagina")
    private Integer pagina;

    @XmlElement(name = "Orgao")
    private String orgao;

    @XmlElement(name = "Juizo")
    private String juizo;

    @XmlElement(name = "Publicacao")
    private String publicacao;

    @XmlElement(name = "CodigoRelacional")
    private Long codigoRelacional;

    @XmlElement(name = "DataCirculacao")
    private Date dataCirculacao;

}
