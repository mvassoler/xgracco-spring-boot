package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoCampo;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoCampoSistema;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoExibicaoCampo;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoCampoConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoCampoSistemaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Table(name = "CAMPO")
@SequenceGenerator(allocationSize = 1, name = "seqCampo", sequenceName = "SEQ_CAMPO")
@Relation(collectionRelation = "campos")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Campo extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqCampo")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", length = 100, nullable = false)
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    private String descricao;

    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CAMPO", nullable = true)
    @NotAudited
    private List<CampoLista> campoLista;

    @Column(name = "ORDEM", precision = 5, scale = 0)
    private Integer ordem;

    @Column(name = "TAMANHO", precision = 5, scale = 0)
    private Integer tamanho;

    @Column(name = "VISIVEL", nullable = false)
    private boolean visivel;

    @Column(name = "OBRIGATORIO", nullable = false)
    private boolean obrigatorio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_GRUPOCAMPO", referencedColumnName = "ID")
    @RelatorioInterface(titulo = "Grupo", unwrapped = true)
    private GrupoCampo grupoCampo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FORMULARIO", referencedColumnName = "ID")
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

    @Column(name = "CONSULTA_SQL")
    private String sql;

    @Column(name = "PAI_LISTA_DINAMICA")
    private boolean paiListaDinamica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CAMPO_PAI", referencedColumnName = "ID")
    private Campo campoPai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CAMPOLISTA_PAI", referencedColumnName = "ID")
    private CampoLista campoListaPai;

    @Transient
    private String listaDescricao;

    @Transient
    private String listaVisivel;

    @Transient
    private String listaId;

    @Transient
    private EnumTipoExibicaoCampo tipoExibicao;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private String caminhoAnexo;

    @Transient
    private String nomePessoa;

    //@TODO -> QUANDO REFATORAR A TELA FORMULÁRIO, REMOVER OS ATRIBUTOS COM A ANOTAÇÃO TRANSIENT E FAZER TUDO VIA API
    @Transient
    private Long idCampoPai;

    @Transient
    private String descricaoCampoPai;

    @Transient
    private Long idCampoListaPai;

    @Transient
    private String descricaoCampoListaPai;

    @Transient
    private boolean paiPoussiValor;

    public Campo() {
    }

    public Campo(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Campo(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @QueryProjection
    public Campo(Long id, String descricao, EnumTipoCampo tipoCampo) {
        this.id = id;
        this.descricao = descricao;
        this.tipoCampo = tipoCampo;
    }

    @QueryProjection
    public Campo(Long id, String descricao, Formulario formulario) {
        this.id = id;
        this.descricao = descricao;
        this.formulario = formulario;
    }

    @QueryProjection
    public Campo(Long id, String descricao, Integer ordem, Integer tamanho, EnumTipoCampo tipoCampo, boolean visivel, boolean obrigatorio, boolean ativo) {
        this.id = id;
        this.descricao = descricao;
        this.ordem = ordem;
        this.tamanho = tamanho;
        this.tipoCampo = tipoCampo;
        this.visivel = visivel;
        this.obrigatorio = obrigatorio;
        this.ativo = ativo;
    }

    @QueryProjection
    public Campo(Long id,
                 String descricao,
                 Integer ordem,
                 Integer tamanho,
                 EnumTipoCampo tipoCampo,
                 boolean visivel,
                 boolean obrigatorio,
                 boolean ativo,
                 Formulario formulario) {
        this.id = id;
        this.descricao = descricao;
        this.ordem = ordem;
        this.tamanho = tamanho;
        this.tipoCampo = tipoCampo;
        this.visivel = visivel;
        this.obrigatorio = obrigatorio;
        this.ativo = ativo;
        this.formulario = formulario;
    }

    @QueryProjection
    public Campo(Long id,
                 String descricao,
                 Integer ordem,
                 Integer tamanho,
                 EnumTipoCampo tipoCampo,
                 boolean visivel,
                 boolean obrigatorio,
                 boolean ativo,
                 Formulario formulario,
                 GrupoCampo grupoCampo) {
        this.id = id;
        this.descricao = descricao;
        this.ordem = ordem;
        this.tamanho = tamanho;
        this.tipoCampo = tipoCampo;
        this.visivel = visivel;
        this.obrigatorio = obrigatorio;
        this.ativo = ativo;
        this.formulario = formulario;
        this.grupoCampo = grupoCampo;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return new StringBuilder(descricao).append(" | Tipo: ").append(tipoCampo.toString()).toString();
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Campo campo = (Campo) o;
        return Objects.equals(this.getId(), campo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
