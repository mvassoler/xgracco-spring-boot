package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoCaseInstance;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Created by Jordano on 30/01/2018.
 */
public class ProcessoCaseInstanceFilter implements Filter<ProcessoCaseInstance> {

    private Processo processo;
    private String caseInstanceId;
    private String deployId;


    public ProcessoCaseInstanceFilter(Processo processo, String caseInstanceId, String deployId) {
        this.processo = processo;
        this.caseInstanceId = caseInstanceId;
        this.deployId = deployId;

    }

    public Processo getProcesso() {
        return processo;
    }

    public String getCaseInstanceId() {
        return caseInstanceId;
    }

    public String getDeployId() {
        return deployId;
    }
}