package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumPoloAtuacao;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumPoloAtuacaoConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Audited
@Relation(collectionRelation = "posicoes")
@Table(name = "POSICAO")
@SequenceGenerator(allocationSize = 1, name = "seqPosicao", sequenceName = "SEQ_POSICAO")
@RelatorioInterface(titulo = "Posição das Partes")
@Data
@Builder
@AllArgsConstructor
public class Posicao implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPosicao")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @RelatorioInterface(titulo = "Posição", padrao = true)
    @Column(name = "DESCRICAO")
    private String descricao;

    @NotBlank
    @Size(max = 100)
    @RelatorioInterface(titulo = "Posição contrária", padrao = true)
    @Column(name = "POSICAO_CONTRARIA")
    private String posicaoContraria;

    @RelatorioInterface(titulo = "Pólo atuação", padrao = true)
    @Column(name = "ID_POLO_ATUACAO")
    @Convert(converter = EnumPoloAtuacaoConverter.class)
    private EnumPoloAtuacao enumPoloAtuacao;

    @Transient
    private LogAuditoria logAuditoria;

    public Posicao() {
    }

    public Posicao(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Posicao(Long id, String descricao, String posicaoContraria, EnumPoloAtuacao enumPoloAtuacao) {
        this.id = id;
        this.descricao = descricao;
        this.posicaoContraria = posicaoContraria;
        this.enumPoloAtuacao = enumPoloAtuacao;
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
    public String toString() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Posicao that = (Posicao) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
