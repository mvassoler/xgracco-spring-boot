package br.com.finchsolucoes.xgracco.legacy.beans.interfaces;

import br.com.finchsolucoes.xgracco.domain.entity.LogAuditoria;

import java.io.Serializable;

/**
 * Created by paulomarcon
 */
public interface EntidadeAuditada extends Serializable {

    LogAuditoria getLogAuditoria();

    void setLogAuditoria(LogAuditoria logAuditoria);
}
