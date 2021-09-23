package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumParametroConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Parametro;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PARAMETROGLOBAL")
@Data
@Builder
public class ParametroGlobal extends Entidade implements Identificavel<Long>, Parametro {

    private static final long serialVersionUID = 6828411022480241841L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "CHAVE")
    @Convert(converter = EnumParametroConverter.class)
    private EnumParametro chave;

    @Column(name = "VALOR")
    private String valor;

    @Column(name = "CLASSE", length = 200)
    private String classe;

    @Column(name = "DESCRICAO")
    private String descricao;

    public ParametroGlobal() {
    }

    public ParametroGlobal(Long id) {
        this.id = id;
    }

    @QueryProjection
    public ParametroGlobal(Long id, EnumParametro chave, String descricao, String classe, String valor) {
        this.id = id;
        this.chave = chave;
        this.descricao = descricao;
        this.classe = classe;
        this.valor = valor;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return id.toString() + " " + chave + " " + classe;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setChave(EnumParametro chave) {
        this.chave = chave;
    }

    @Override
    public Long getIdPessoa() {
        return null;
    }

    @Override
    public void setIdPessoa(Long idPessoa) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ParametroGlobal that = (ParametroGlobal) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
