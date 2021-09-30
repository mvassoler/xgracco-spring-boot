package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Andre Farina
 */
@Entity
@Component
@Table(name = "PROCESSOSFAVORITOS")
@SequenceGenerator(allocationSize = 1, name = "seqProcessosFavoritos", sequenceName = "SEQ_PROCESSOSFAVORITOS")
@Data
@Builder
@AllArgsConstructor
public class ProcessosFavoritos extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqProcessosFavoritos")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    public ProcessosFavoritos() {
    }

    public ProcessosFavoritos(Long id) {
        this.id = id;
    }

    @QueryProjection
    public ProcessosFavoritos(Long id, Processo processo) {
        this.id = id;
        this.processo = processo;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        if (pessoa != null && processo != null) {
            return new StringBuilder("Usuario: ").append(pessoa.getNomeRazaoSocial()).append(" | Processo: ").append(processo.getTextoLog()).toString();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ProcessosFavoritos that = (ProcessosFavoritos) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
