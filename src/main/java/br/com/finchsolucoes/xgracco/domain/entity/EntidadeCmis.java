package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.listeners.AbstractEntidadeCmisListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by renan on 20/09/16.
 */
@MappedSuperclass
@EntityListeners(AbstractEntidadeCmisListener.class)
public abstract class EntidadeCmis extends Entidade implements Serializable {

    public abstract void setRetorno(String retorno);

    public abstract String getCaminho();

    public abstract String getDescricao();

    public abstract Entidade getEntidade();

}
