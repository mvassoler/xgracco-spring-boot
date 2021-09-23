package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Implementação da entidade Rotina Parâmetro.
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
@Entity
@Table(name = "ROTINA_PARAMETRO")
@SequenceGenerator(allocationSize = 1, name = "seqRotinaParametro", sequenceName = "SEQ_ROTINA_PARAMETRO")
@Audited
@Data
@Builder
@AllArgsConstructor
public class RotinaParametro implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqRotinaParametro")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROTINA_ID", updatable = false)
    @JsonIgnore
    private Rotina rotina;

    @NotBlank
    @Column(name = "PARAMETRO", updatable = false)
    private String parametro;

    @Column(name = "VALOR")
    private String valor;

    @Transient
    private LogAuditoria logAuditoria;

    public RotinaParametro() {
    }

    public RotinaParametro(Long id) {
        this.id = id;
    }

    public RotinaParametro(String parametro, String valor) {
        this.parametro = parametro;
        this.valor = valor;
    }

    public RotinaParametro(Rotina rotina, String parametro, String valor) {
        this.rotina = rotina;
        this.parametro = parametro;
        this.valor = valor;
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
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        RotinaParametro rotinaParametro = (RotinaParametro) o;
        return Objects.equals(this.getParametro(), rotinaParametro.getParametro());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
