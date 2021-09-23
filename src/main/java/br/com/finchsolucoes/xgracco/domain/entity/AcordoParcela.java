package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author Jordano
 */
@Entity
@Table(name = "ACORDOPARCELA")
@SequenceGenerator(allocationSize = 1, name = "seqAcordoParcela", sequenceName = "SEQ_ACORDOPARCELA")
@Data
@Builder
@AllArgsConstructor
public class AcordoParcela extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqAcordoParcela")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PAGO")
    private Boolean pago;

    @Temporal(TemporalType.DATE)
    @Column(name = "VENCIMENTO")
    private Calendar dataVencimento;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATAPAGAMENTO")
    private Calendar dataPagamento;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ACORDO")
    private Acordo acordo;

    @Column(name = "AGENDAMENTO")
    private Boolean gerarAgendamento;

    public AcordoParcela() {
    }

    public AcordoParcela(Long id) {
        this.id = id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return valor.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        AcordoParcela that = (AcordoParcela) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
