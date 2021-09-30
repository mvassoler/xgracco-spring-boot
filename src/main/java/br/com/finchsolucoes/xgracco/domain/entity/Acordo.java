package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author Jordano
 */
@Entity
@Table(name = "ACORDO")
@SequenceGenerator(allocationSize = 1, name = "seqAcordo", sequenceName = "SEQ_ACORDO")
@Data
@Builder
@AllArgsConstructor
public class Acordo extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqAcordo")
    @Column(name = "ID")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA")
    private Calendar data;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "MEMO")
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "acordo", orphanRemoval = true)
    private List<AcordoParcela> acordoParcela;

    @Column(name = "VALOR_PAGO")
    private BigDecimal valorPago;

    @Column(name = "AGENDAMENTO")
    private Boolean gerarAgendamento;

    public Acordo() {
    }

    public Acordo(Long id) {
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
        Acordo acordo = (Acordo) o;
        return Objects.equals(this.getId(), acordo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
