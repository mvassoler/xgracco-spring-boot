package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * Entidade responsável por ser o cabeçalho de um agrupamento de processos que serão considerados
 * para captura de novos andamentos (integração com o Catcher).
 *
 * @author andre.baroni
 */
@Entity
@Relation(collectionRelation = "capturaAndamentos")
@Table(name = "captura_andamento")
@SequenceGenerator(allocationSize = 1, name = "seqCapturaAndamento", sequenceName = "seq_capturaandamento")
@Audited
@Data
@Builder
@AllArgsConstructor
public class CapturaAndamento implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqCapturaAndamento")
    @Column(name = "id")
    private Long id;

    //@NotNull
    //@N/otEmpty
    //@Size(min = 2, max = 100)
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "capturaAndamento", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Collection<CapturaAndamentoProcesso> capturaAndamentoProcessos;

    @OneToMany(mappedBy = "capturaAndamento", fetch = FetchType.LAZY)
    private Collection<Rotina> rotinas;

    @OneToMany(mappedBy = "capturaAndamento", fetch = FetchType.LAZY)
    private Collection<SolicitacaoAndamento> solicitacaoAndamentos;

    @Transient
    @JsonProperty("quantidadeProcessos")
    private Long quantidadeProcessos;

    @Transient
    private LogAuditoria logAuditoria;

    public CapturaAndamento() {
        super();
    }

    public CapturaAndamento(String descricao) {
        this();
        this.setDescricao(descricao);
    }

    @QueryProjection
    public CapturaAndamento(Long id, String descricao, Long quantidadeProcessos) {
        this();
        this.setId(id);
        this.setDescricao(descricao);
        this.setQuantidadeProcessos(quantidadeProcessos);
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return this.logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CapturaAndamento)) return false;
        CapturaAndamento that = (CapturaAndamento) o;
        return this.getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
