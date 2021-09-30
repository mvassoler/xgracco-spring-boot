package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumEvento;
import br.com.finchsolucoes.xgracco.domain.enums.EnumFuncao;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumEventoConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumFuncaoConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Created by renan on 04/10/16.
 */
@Entity
@Table(name = "CARTEIRA_EVENTO")
@SequenceGenerator(allocationSize = 1, name = "seqCarteiraEvento", sequenceName = "SEQ_CARTEIRA_EVENTO")
@Data
@Builder
@AllArgsConstructor
public class CarteiraEvento extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqCarteiraEvento")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CARTEIRA")
    private Carteira carteira;

    @Column(name = "ID_EVENTO")
    @Convert(converter = EnumEventoConverter.class)
    private EnumEvento evento;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "ATIVO")
    private boolean ativo;

    @ElementCollection(targetClass = EnumFuncao.class)
    @CollectionTable(name = "CARTEIRA_EVENTO_FUNCAO", joinColumns = @JoinColumn(name = "ID_CARTEIRA_EVENTO"))
    @Column(name = "ID_FUNCAO")
    @Convert(converter = EnumFuncaoConverter.class)
    private List<EnumFuncao> funcoes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "carteiraEvento")
    private List<CarteiraEventoTarefa> eventoTarefas;

    public CarteiraEvento() {
    }

    public CarteiraEvento(Long id) {
        this.id = id;
    }

    @QueryProjection
    public CarteiraEvento(Long id, Carteira carteira, EnumEvento evento, String nome) {
        this.id = id;
        this.carteira = carteira;
        this.evento = evento;
        this.nome = nome;
    }

    @QueryProjection
    public CarteiraEvento(Carteira carteira, String nome) {
        this.carteira = carteira;
        this.nome = nome;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        CarteiraEvento carteiraEvento = (CarteiraEvento) o;
        return Objects.equals(this.getId(), carteiraEvento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
