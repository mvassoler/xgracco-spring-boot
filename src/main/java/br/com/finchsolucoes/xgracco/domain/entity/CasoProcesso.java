package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.annotations.QueryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.List;

@Entity
@Relation(collectionRelation = "casos-processos")
@SequenceGenerator(allocationSize = 1, name = "seqCasoProcesso", sequenceName = "SEQ_CASO_PROCESSO")
@Table(name = "CASO_PROCESSO")
@Data
@Builder
@AllArgsConstructor
public class CasoProcesso implements EntidadeAuditada {

    private static final long serialVersionUID = 1598515206590597506L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqCasoProcesso")
    @Column(name = "ID")
    private Long id;

    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CASO", referencedColumnName = "ID", nullable = false)
    private Caso caso;

    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROCESSO", referencedColumnName = "ID", nullable = false)
    private Processo processo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PAI", referencedColumnName = "ID")
    private CasoProcesso casoProcessoPai;

    @OneToMany(mappedBy = "casoProcessoPai")
    private List<CasoProcesso> processos;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    @QueryType(PropertyType.NUMERIC)
    private long quantidadeProcessos;

    @QueryProjection
    public CasoProcesso(Caso caso) {
        this.caso = caso;
    }

    @QueryProjection
    public CasoProcesso() {
    }

    @QueryProjection
    public CasoProcesso(Long id){
        this.id = id;
    }

    @QueryProjection
    public CasoProcesso(Long id, Caso caso, Processo processo, CasoProcesso casoProcessoPai, Long quantidadeProcessos) {
        this.id = id;
        this.caso = caso;
        this.processo = processo;
        this.casoProcessoPai = casoProcessoPai;
        this.quantidadeProcessos = quantidadeProcessos;
    }

    @QueryProjection
    public CasoProcesso(Long id, Caso caso, Processo processo) {
        this.id = id;
        this.caso = caso;
        this.processo = processo;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CasoProcesso)) return false;

        CasoProcesso that = (CasoProcesso) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getCaso() != null ? !getCaso().equals(that.getCaso()) : that.getCaso() != null) return false;
        if (getProcesso() != null ? !getProcesso().equals(that.getProcesso()) : that.getProcesso() != null)
            return false;
        if (getCasoProcessoPai() != null ? !getCasoProcessoPai().equals(that.getCasoProcessoPai()) : that.getCasoProcessoPai() != null)
            return false;
        if (getProcessos() != null ? !getProcessos().equals(that.getProcessos()) : that.getProcessos() != null)
            return false;
        if (getLogAuditoria() != null ? !getLogAuditoria().equals(that.getLogAuditoria()) : that.getLogAuditoria() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getCaso() != null ? getCaso().hashCode() : 0);
        result = 31 * result + (getProcesso() != null ? getProcesso().hashCode() : 0);
        result = 31 * result + (getCasoProcessoPai() != null ? getCasoProcessoPai().hashCode() : 0);
        result = 31 * result + (getProcessos() != null ? getProcessos().hashCode() : 0);
        result = 31 * result + (getLogAuditoria() != null ? getLogAuditoria().hashCode() : 0);
        return result;
    }
}
