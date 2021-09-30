package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoCampo;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoCampoSistema;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoCampoConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoCampoSistemaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Created by jordano on 31/08/16.
 */

@Entity
@Table(name = "VW_CAMPO")
@RelatorioInterface(titulo = "Campo")
@Data
@Builder
@AllArgsConstructor
public class VWCampo extends Entidade implements Identificavel<Long> {

    @Id
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Campo Descrição", label = "Campo Descrição", padrao = true)
    @Column(name = "DESCRICAO", length = 100, nullable = false)
    private String descricao;

    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CAMPO", nullable = true)
    private List<CampoLista> campoLista;

    @Column(name = "ORDEM", precision = 5, scale = 0)
    private Integer ordem;

    @Column(name = "TAMANHO", precision = 5, scale = 0)
    private Integer tamanho;

    @Column(name = "VISIVEL", nullable = false)
    private boolean visivel;

    @Column(name = "OBRIGATORIO", nullable = false)
    private boolean obrigatorio;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_GRUPOCAMPO", insertable = false, updatable = false, nullable = true)
    private GrupoCampo grupoCampo;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FORMULARIO", insertable = false, updatable = false, nullable = true)
    private Formulario formulario;

    @Column(name = "TIPO_CAMPO")
    @Convert(converter = EnumTipoCampoConverter.class)
    private EnumTipoCampo tipoCampo;

    @Column(name = "SISTEMA")
    private boolean sistema = false;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Column(name = "TIPO_CAMPO_SISTEMA")
    @Convert(converter = EnumTipoCampoSistemaConverter.class)
    private EnumTipoCampoSistema tipoCampoSistema;

    @Column(name = "TAMANHOMAXIMO", precision = 10, scale = 0)
    private Long tamanhomaximo;

    @Column(name = "ATIVO", nullable = false)
    private boolean ativo;

    @Column(name = "EXTENSAO")
    private String extensao;

    @Column(name = "ID_NAME")
    private Long idName;

    public VWCampo() {
    }

    public VWCampo(Long id) {
        this.id = id;
    }

    @Override
    public Long getPK() {
        return null;
    }

    @Override
    public String getTextoLog() {
        return null;
    }

    @Override
    public Long getId() {
        return null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        VWCampo vwCampo = (VWCampo) o;
        return Objects.equals(this.getId(), vwCampo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
