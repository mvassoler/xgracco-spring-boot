package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Jordano TODO Entity sem crud - insert na base
 */
@Entity
@Table(name = "GRUPOENCERRAMENTO")
@SequenceGenerator(allocationSize = 1, name = "seqGrupoEncerramento", sequenceName = "SEQ_GRUPOENCERRAMENTO")
@Data
@Builder
public class GrupoEncerramento extends Entidade implements Identificavel<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqGrupoEncerramento")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", length = 200)
    private String descricao;

    public GrupoEncerramento() {
    }

    public GrupoEncerramento(Long id) {
        this.id = id;
    }

    public GrupoEncerramento(String descricao) {
        this.descricao = descricao;
    }

    public GrupoEncerramento(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        GrupoEncerramento that = (GrupoEncerramento) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
