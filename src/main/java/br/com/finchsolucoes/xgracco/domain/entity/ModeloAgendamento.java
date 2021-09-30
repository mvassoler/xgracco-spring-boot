package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoDestinatario;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoDestinatarioConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Relation(collectionRelation = "modelos-agendamento")
@Table(name = "MODELOGENDAMENTO")
@SequenceGenerator(allocationSize = 1, name = "seqModeloAgendamento", sequenceName = "SEQ_MODELOAGENDAMENTO")
@Data
@Builder
@AllArgsConstructor
public class ModeloAgendamento extends Entidade implements Identificavel<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqModeloAgendamento")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", length = 100, nullable = false)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_GRUPOAGENDAMENTO", nullable = false)
    private GrupoAgendamento grupoAgendamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHOTAREFA")
    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    @Column(name = "COPIAR_MEMO_PUBLICACAO")
    private boolean copiarTextoPublicacao = false;

    @Column(name = "PAI")
    private boolean pai = false;

    @Column(name = "MEMO_PADRAO")
    private String memoPadrao;

    @Column(name = "DIAS_AGENDAMENTO")
    private Integer diasAgendamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @Column(name = "TIPO_DESTINATARIO")
    @Convert(converter = EnumTipoDestinatarioConverter.class)
    private EnumTipoDestinatario tipoDestinatario;

    @Transient
    private Calendar dataPai;

    @Transient
    private String dataPaiAux;

    public ModeloAgendamento() {
    }

    public ModeloAgendamento(Long id) {
        this.id = id;
    }

    @QueryProjection
    public ModeloAgendamento(String descricao, GrupoAgendamento grupoAgendamento) {
        this.descricao = descricao;
        this.grupoAgendamento = grupoAgendamento;
    }

    @QueryProjection
    public ModeloAgendamento(Long id, String descricao, GrupoAgendamento grupoAgendamento, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, String memoPadrao, Pessoa pessoa, EnumTipoDestinatario tipoDestinatario) {
        this.id = id;
        this.descricao = descricao;
        this.grupoAgendamento = grupoAgendamento;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.memoPadrao = memoPadrao;
        this.pessoa = pessoa;
        this.tipoDestinatario = tipoDestinatario;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public Long getId() {
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
        ModeloAgendamento that = (ModeloAgendamento) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
