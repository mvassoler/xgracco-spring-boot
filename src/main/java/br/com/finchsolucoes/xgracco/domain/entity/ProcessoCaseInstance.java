package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author rodolpho.couto
 */
@Entity
@Table(name = "PROCESSO_CASE_INSTANCE")
@Data
@Builder
public class ProcessoCaseInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_CASE_INSTANCE")
    @RelatorioInterface(ignore = true)
    private String caseInstanceId;

    @RelatorioInterface(unwrapped = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROCESSO", referencedColumnName = "ID")
    private Processo processo;

    @RelatorioInterface(ignore = true)
    @Column(name = "DEPLOYID")
    private String deployId;

    public ProcessoCaseInstance() {
    }

    public ProcessoCaseInstance(String caseInstanceId, Processo processo, String deployId) {
        this.caseInstanceId = caseInstanceId;
        this.processo = processo;
        this.deployId = deployId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ProcessoCaseInstance that = (ProcessoCaseInstance) o;
        return Objects.equals(caseInstanceId, that.caseInstanceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseInstanceId);
    }
}
