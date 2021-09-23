package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Jordano
 */
@Entity
@Table(name = "ACORDOPARCELAAGENDA")
@SequenceGenerator(allocationSize = 1, name = "seqAcordoParcelaAgenda", sequenceName = "SEQ_ACORDOPARCELAAGENDA")
@Data
@Builder
@AllArgsConstructor
public class AcordoParcelaAgenda extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqAcordoParcelaAgenda")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PK_ACORDOPARCELA")
    private AcordoParcela parcela;

    public AcordoParcelaAgenda() {
    }

    public AcordoParcelaAgenda(Long id) {
        this.id = id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return parcela.getValor().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        AcordoParcelaAgenda that = (AcordoParcelaAgenda) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
