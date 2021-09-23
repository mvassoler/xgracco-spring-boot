package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumRelatorioCampoFiltro;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoDadoFiltro;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumRelatorioCampoFiltroConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoDadoFiltroConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.views.ObjetoListaValoresRelatorio;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author maiconcarraro
 */
@Entity
@Table(name = "RELATORIOCAMPO")
@SequenceGenerator(allocationSize = 1, name = "seqRelatorioCampo", sequenceName = "SEQ_RELATORIOCAMPO")
@Data
@Builder
@AllArgsConstructor
public class RelatorioCampo extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqRelatorioCampo")
    @Column(name = "ID")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RELATORIOPERFIL")
    private RelatorioPerfil relatorioPerfil;

    @Column(name = "POSICAO")
    private Integer posicao;

    @Column(name = "CLASSE", nullable = false)
    private Class classe;

    @Column(name = "CAMPO", length = 100, nullable = false)
    private String campo;

    @Column(name = "TITULO", length = 100, nullable = false)
    private String titulo;

    @Column(name = "VALORFILTRO1", length = 100)
    private String valorFiltro1;

    @Column(name = "VALORFILTRO2", length = 100)
    private String valorFiltro2;

    @Column(name = "FILTRO")
    @Convert(converter = EnumRelatorioCampoFiltroConverter.class)
    private EnumRelatorioCampoFiltro filtro;

    @Column(name = "CAMPO_RELATORIO")
    private boolean campoRelatorio;

    @Column(name = "FILTRO_RELATORIO")
    private boolean filtroRelatorio;

    @Column(name = "FILTRO_VISIVEL")
    private boolean filtroVisivel;

    @Column(name = "FILTRO_OBRIGATORIO")
    private Boolean filtroObrigatorio;

    @Column(name = "FILTRO_TIPO_DADO")
    @Convert(converter = EnumTipoDadoFiltroConverter.class)
    private EnumTipoDadoFiltro tipoDadoFiltro;

    @Transient
    private Map<Integer, String> opcoes;

    @Transient
    private Map<Object, String> select;

    @Transient
    private Object valor1;

    @Transient
    private Object valor2;

    @Transient
    private List<Long> valor3;

    @Transient
    private String campoTitulo;

    @Transient
    private String classeAtributo;

    @Transient
    private List<ObjetoListaValoresRelatorio> objs;

    public RelatorioCampo() {
    }

    public RelatorioCampo(Long id) {
        this.id = id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return this.titulo;
    }

    public Integer getFiltroId() {
        if (filtro != null) {
            return filtro.getId();
        } else if (opcoes != null && !opcoes.isEmpty()) {
            return (Integer) opcoes.keySet().toArray()[0];
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        RelatorioCampo that = (RelatorioCampo) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
