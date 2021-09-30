package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author felipeinoue
 */
@Entity
@Table(name = "DADO")
@SequenceGenerator(allocationSize = 1, name = "seqDado", sequenceName = "SEQ_DADO")
@Data
@Builder
@AllArgsConstructor
public class Dado extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqDado")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONTEUDO")
    private String conteudo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_CAMPO")
    private Campo campo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CAMPOLISTA")
    private CampoLista campoLista;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    public Dado() {
    }

    public Dado(Long id) {
        this.id = id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return conteudo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Dado dado = (Dado) o;
        return Objects.equals(this.getId(), dado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
