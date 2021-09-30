package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/*
 *ENTIDADE CRIADA PARA CLASSIFICAR UM TERCEIRO, ASSIM
 * SUBISTITUINDO A TABELA ATIVIDADE
 */

/**
 * @author rianmachado
 */
@Entity
@Table(name = "GRUPOATIVIDADE", uniqueConstraints = @UniqueConstraint(columnNames = {"GRUPOATIVIDADE_DESCRICAO"}))
@SequenceGenerator(allocationSize = 1, name = "seqGrupoAtividade", sequenceName = "SEQ_GRUPOATIVIDADE")
@Data
@Builder
@AllArgsConstructor
public class GrupoAtividade extends Entidade implements Identificavel<Long> {

    public static final String PARTE_INTERESSADA = "PARTE INTERESSADA";
    public static final String PARTE_CONTRARIA = "PARTE CONTR\u00c1RIA";
    public static final String ADVOGADO_INTERESSADO = "ADVOGADO INTERESSADO";
    public static final String ADVOGADO_CONTRARIO = "ADVOGADO CONTR\u00c1RIO";
    public static final String CLIENTE = "CLIENTE";
    public static final String ESTAGIARIO = "ESTAGI\u00c1RIO";
    public static final String ESCRITORIO = "ESCRITORIO";
    public static final String REGIONAL = "REGIONAL";
    public static final String ADVOGADO_RESPONSAVEL = "ADVOGADO RESPONS\u00c1VEL";
    public static final String ADVOGADO = "ADVOGADO";
    private static final long serialVersionUID = 1L;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "AUX_TERCEIRO_GRUPOATIVIDADE", joinColumns = @JoinColumn(name = "GRUPOATIVIDADE_ID"), inverseJoinColumns = @JoinColumn(name = "TERCEIRO_ID"))
    private List<Pessoa> terceiros;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqGrupoAtividade")
    @Column(name = "GRUPOATIVIDADE_ID")
    private Long id;

    @Column(name = "GRUPOATIVIDADE_DESCRICAO", length = 100, nullable = false)
    private String descricao;

    public GrupoAtividade() {
    }

    @Override
    public Long getPK() {
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
        GrupoAtividade that = (GrupoAtividade) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
