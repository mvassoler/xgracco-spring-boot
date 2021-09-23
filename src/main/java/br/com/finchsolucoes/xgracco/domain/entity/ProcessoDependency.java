package br.com.finchsolucoes.xgracco.domain.entity;

import java.io.Serializable;

/**
 * @author Jordano
 * @since 2.2
 */
public interface ProcessoDependency extends Serializable {

    Processo getProcesso();

}
