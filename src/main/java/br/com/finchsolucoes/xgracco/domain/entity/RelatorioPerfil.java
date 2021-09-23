package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * @author maiconcarraro
 */
@Entity
@Table(name = "RELATORIOPERFIL")
@SequenceGenerator(allocationSize = 1, name = "seqRelatorioPerfil", sequenceName = "SEQ_RELATORIOPERFIL")
@Data
@Builder
@AllArgsConstructor
public class RelatorioPerfil extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqRelatorioPerfil")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CLASSE", nullable = false)
    private Class classe;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PESSOA")
    private Pessoa usuario;

    @OneToMany(mappedBy = "relatorioPerfil", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RelatorioCampo> campos;

    @Column(name = "TITULO", length = 100, nullable = false)
    private String titulo;

    @Column(name = "TEMPLATE", length = 100, nullable = false)
    private String template;

    @Column(name = "PUBLICO")
    private boolean publico;

    @Column(name = "PERMITIR_EDICAO")
    private boolean permitirEdicao;

    @Column(name = "CONSULTA_SQL")
    private String sql;

    @Column(name = "SENHA", length = 100)
    private String senha;

    @Size(max = 100)
    @Column(name = "PROCEDURE_NAME")
    private String procedureName;

    @Transient
    private List<RelatorioCampo> camposExtras;

    @Transient
    private List<RelatorioPerfil> templates;

    @Transient
    private String classeName;

    public RelatorioPerfil() {
        if (this.getClasse() != null) {
            this.setClasseName(this.getClasse().getName());
        }
    }

    public RelatorioPerfil(Long id) {
        this.id = id;
    }

    public RelatorioPerfil(Long id, Class classe, String template, String titulo) {
        this.id = id;
        this.classe = classe;
        this.template = template;
        this.titulo = titulo;
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

    public String getClasseName() {
        if (StringUtils.isBlank(this.classeName) && this.classe != null) {
            this.classeName = this.classe.getSimpleName();
        }
        return classeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        RelatorioPerfil that = (RelatorioPerfil) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
