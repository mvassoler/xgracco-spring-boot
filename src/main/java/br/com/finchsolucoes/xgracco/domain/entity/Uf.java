package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Audited
@Table(name = "UF")
@Relation(collectionRelation = "ufs")
@RelatorioInterface(titulo = "Estados")
@Data
@Builder
@AllArgsConstructor
public class Uf {

    @Id
    @Column(name = "ID")
    @RelatorioInterface(titulo = "ID")
    private Long id;

    @Column(name = "UF_ICMS")
    private BigDecimal UF_ICMS;

    @Column(name = "NOME")
    @RelatorioInterface(titulo = "Nome")
    private String nome;

    @Column(name = "SIGLA")
    @RelatorioInterface(titulo = "Sigla")
    private String sigla;

    @Column(name = "CODIGO_EXTERNO")
    private Integer codigoExterno;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGIAO", referencedColumnName = "ID")
    @RelatorioInterface(titulo = "Região")
    private RegiaoUf regiao;

    @JsonIgnore
    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SISTEMAVIRTUAL_UF", joinColumns = @JoinColumn(name = "UF_ID"), inverseJoinColumns = @JoinColumn(name = "SISTEMAVIRTUAL_ID"))
    private List<SistemaVirtual> sistemasVirtuais;

    public Uf(Long id) {
        this.id = id;
    }

    public Uf(){

    }

    @QueryProjection
    public Uf(Long id, String nome, String sigla) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
    }

    public Uf(Long id, BigDecimal UF_ICMS, String nome, String sigla, Integer codigoExterno, RegiaoUf regiao) {
        this.id = id;
        this.UF_ICMS = UF_ICMS;
        this.nome = nome;
        this.sigla = sigla;
        this.codigoExterno = codigoExterno;
        this.regiao = regiao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Uf uf = (Uf) o;
        return Objects.equals(this.getId(), uf.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return sigla;
    }

}
