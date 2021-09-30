package br.com.finchsolucoes.xgracco.domain.entity;

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
 * @author Laerte
 */
@Entity
@Table(name = "FORMULARIO_LOG")
@SequenceGenerator(allocationSize = 1, name = "SEQ_FORMULARIOLOG", sequenceName = "SEQ_FORMULARIOLOG")
@Data
@Builder
@AllArgsConstructor
public class FormularioLog extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_FORMULARIOLOG")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME", length = 255, nullable = false)
    private String nome;

    @Column(name = "IDENTIFICADOR", length = 255)
    private String identificador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FORMULARIO")
    private Formulario formulario;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_FORMULARIOLOG")
    private List<CampoLog> campos;

    @Column(name = "VERSAO", nullable = false)
    private Long versao;

    public FormularioLog() {
    }

    public FormularioLog(Long id) {
        this.id = id;
    }

    @QueryProjection
    public FormularioLog(Long id, Long versao) {
        this.id = id;
        this.versao = versao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return id + " - " + nome;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public List<CampoLog> getCampos() {
        return campos;
    }

    public void setCampos(List<CampoLog> campos) {
        this.campos = campos;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        FormularioLog that = (FormularioLog) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
