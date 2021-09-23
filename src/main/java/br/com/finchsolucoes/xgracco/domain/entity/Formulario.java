package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoExibicaoCampo;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoExibicaoCampoConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * @author Andre Farina
 */
@Entity
@Table(name = "FORMULARIO", uniqueConstraints = {@UniqueConstraint(columnNames = {"nome"}, name = "nome"), @UniqueConstraint(columnNames = {"identificador"}, name = "identificador")})
@SequenceGenerator(allocationSize = 1, name = "seqFormulario", sequenceName = "SEQ_FORMULARIO")
@Audited
@Relation(collectionRelation = "formularios")
@Data
@Builder
@AllArgsConstructor
public class Formulario extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqFormulario")
    @Column(name = "ID")
    private Long id;

    @NotEmpty
    @NotNull
    @Column(name = "NOME", length = 255)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FORMULARIO_PAI", referencedColumnName = "ID")
    private Formulario formularioPai;

    @Column(name = "ID_TIPO_EXIBICAO_PAI")
    @Convert(converter = EnumTipoExibicaoCampoConverter.class)
    private EnumTipoExibicaoCampo tipoExibicaoFormularioPai;

    @OrderBy("ordem")
    @OneToMany(mappedBy = "formulario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @NotAudited
    private List<Campo> campos;

    @Column(name = "IDENTIFICADOR", length = 255)
    private String identificador;

    @Column(name = "SISTEMA")
    private boolean sistema;

    @Transient
    private String descricaoCampo;

    @Transient
    private Long idTipoCampo;

    @OneToMany(mappedBy = "formulario", fetch = FetchType.LAZY)
    @NotAudited
    private List<FormularioLog> formularioLog;

    @OneToMany(mappedBy = "formulario", fetch = FetchType.LAZY)
    @NotAudited
    private List<StatusFinal> statusFinais;

    @Transient
    private Long versao;

    @Transient
    private LogAuditoria logAuditoria;

    public Formulario() {
    }

    @QueryProjection
    public Formulario(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Formulario(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @QueryProjection
    public Formulario(Long id, String nome, Long versao) {
        this.id = id;
        this.nome = nome;
        this.versao = versao;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return id + " - " + nome;
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
        Formulario that = (Formulario) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
