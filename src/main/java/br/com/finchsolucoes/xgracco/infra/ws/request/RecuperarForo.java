package br.com.finchsolucoes.xgracco.infra.ws.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Raphael Moreira
 */
@XmlRootElement(name = "Foro")
public class RecuperarForo implements Serializable {

    private Long id;
    private String foroDesc;
    private Long idComarca;
    private String comarcaDesc;
    private String uf;
    private Boolean sugerido;

    @XmlElement(name = "IDForo")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "ForoDesc")
    public String getForoDesc() {
        return foroDesc;
    }

    public void setForoDesc(String foroDesc) {
        this.foroDesc = foroDesc;
    }

    @XmlElement(name = "IDComarca")
    public Long getIdComarca() {
        return idComarca;
    }

    public void setIdComarca(Long idComarca) {
        this.idComarca = idComarca;
    }

    @XmlElement(name = "ComarcaDesc")
    public String getComarcaDesc() {
        return comarcaDesc;
    }

    public void setComarcaDesc(String comarcaDesc) {
        this.comarcaDesc = comarcaDesc;
    }

    @XmlElement(name = "UF")
    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Boolean getSugerido() {
        return sugerido;
    }

    public void setSugerido(Boolean sugerido) {
        this.sugerido = sugerido;
    }
}
