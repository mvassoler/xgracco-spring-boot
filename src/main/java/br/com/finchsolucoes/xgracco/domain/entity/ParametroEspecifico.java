package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Parametro;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PARAMETROESPECIFICO")
@SequenceGenerator(allocationSize = 1, name = "seqParametroEspecifico", sequenceName = "SEQ_PARAMETROESPECIFICO")
@Data
@Builder
@AllArgsConstructor
public class ParametroEspecifico extends Entidade implements Identificavel<Long>, Parametro {

    private static final long serialVersionUID = 2519565277748056174L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqParametroEspecifico")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PARAMETROGLOBAL", nullable = false)
    private ParametroGlobal parametro;

    @Column(name = "VALOR", length = 255)
    private String valor;

    @Column(name = "FK_PESSOA")
    private Long idPessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA", insertable = false, updatable = false)
    private Pessoa pessoa;

    public ParametroEspecifico() {
    }

    public ParametroEspecifico(Long id) {
        this.id = id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return id.toString() + " especifico " + parametro.getTextoLog();
    }

    @Override
    public Long getId() {
        return id;
    }


    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        if (pessoa != null && pessoa.getId() != null) {
            this.idPessoa = pessoa.getId();
        }
    }

    @Override
    public EnumParametro getChave() {
        if (parametro != null) {
            return parametro.getChave();
        }
        return null;
    }

    @Override
    public void setChave(EnumParametro chave) {
        if (parametro != null) {
            parametro.setChave(chave);
        }
    }

    @Override
    public String getClasse() {
        if (parametro != null) {
            return parametro.getClasse();
        }
        return null;
    }

    @Override
    public void setClasse(String classe) {
        if (parametro != null) {
            parametro.setClasse(classe);
        }
    }

    @Override
    public String getDescricao() {
        if (parametro != null) {
            return parametro.getDescricao();
        }
        return null;
    }

    @Override
    public void setDescricao(String descricao) {
        if (parametro != null) {
            parametro.setDescricao(descricao);
        }
    }

    public void copy(Parametro parametro) {
        setValor(parametro.getValor());
        setChave(parametro.getChave());
        setClasse(parametro.getClasse());
        setDescricao(parametro.getDescricao());
        setIdPessoa(parametro.getIdPessoa());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ParametroEspecifico that = (ParametroEspecifico) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
