package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumCondicao;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumCondicaoConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by renan on 04/10/16.
 */
@Entity
@Table(name = "CARTEIRA_EVENTO_TAREFA_TAG")
@SequenceGenerator(allocationSize = 1, name = "seqCarteiraEventoTarefaTag", sequenceName = "SEQ_CARTEIRA_EVENTO_TAREFA_TAG")
@Data
@Builder
@AllArgsConstructor
public class CarteiraEventoTarefaTag extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    //@JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqCarteiraEventoTarefaTag")
    @Column(name = "ID")
    private Long id;

    //@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CARTEIRA_EVENTO_TAREFA", referencedColumnName = "ID")
    private CarteiraEventoTarefa carteiraEventoTarefa;

    @Column(name = "VALOR")
    private String valor;

    @Column(name = "condicao")
    @Convert(converter = EnumCondicaoConverter.class)
    private EnumCondicao condicao;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PAI", referencedColumnName = "ID")
    private CarteiraEventoTarefaTag tagPai;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "tagPai")
    private List<CarteiraEventoTarefaTag> rules = new ArrayList<>();

    @Transient
    private Boolean valid;

    public CarteiraEventoTarefaTag() {
    }

    public CarteiraEventoTarefaTag(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }


    @JsonIgnore
    @Override
    public Long getPK() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getTextoLog() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        CarteiraEventoTarefaTag carteiraEventoTarefa = (CarteiraEventoTarefaTag) o;
        return Objects.equals(this.getId(), carteiraEventoTarefa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
