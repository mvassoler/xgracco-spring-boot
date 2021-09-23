package br.com.finchsolucoes.xgracco.legacy.beans.listeners;

import java.io.Serializable;

/**
 * Created by renan on 20/09/16.
 */
public class AbstractEntidadeCmisListener implements Serializable {

    //TODO - ACERTAR ESTA CLASSE

    /*@Autowired
    private CmisSession cmis;

    @PrePersist
    public void prePersist(EntidadeCmis entidade) throws AlfrescoException {
        AutowireHelper.autowire(this, this.cmis);

        if (entidade.getId() == null) {
            entidade.setRetorno(cmis.copyFromTemp(entidade.getCaminho(), entidade.getEntidade(), entidade.getDescricao()));
        }
    }

    @PostRemove
    public void postRemove(EntidadeCmis entidade) throws AlfrescoException {
        if(!(entidade instanceof ProcessoDespesaArquivo)) {
            AutowireHelper.autowire(this, this.cmis);

            cmis.delete(entidade.getCaminho());
        }
    }*/

}
