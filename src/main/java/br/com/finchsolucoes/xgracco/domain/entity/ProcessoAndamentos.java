package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author Marcelo Aguiar -> 18/04/2016
 */
@Entity
@Table(name = "PROCESSOANDAMENTOS")
@RelatorioInterface(titulo = "Andamentos")
@Relation(collectionRelation = "processoAndamentos")
@SequenceGenerator(allocationSize = 1, name = "seqProcessoAndamentos", sequenceName = "SEQ_PROCESSOANDAMENTOS")
@Data
@Builder
@AllArgsConstructor
public class ProcessoAndamentos extends Entidade implements Identificavel<Long> {

    private static final Long serialVersionUID = 1L;

    @Id
    @RelatorioInterface(ignore = true)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqProcessoAndamentos")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @RelatorioInterface(titulo = "Processo", padrao = true, unwrapped = true)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @RelatorioInterface(ignore = true)
    @Column(name = "FK_PROCESSO", insertable = false, updatable = false)
    private Long idProcesso;

    @RelatorioInterface(titulo = "Data de Captura")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATACAPTURA")
    private Calendar dataCaptura;

    @RelatorioInterface(titulo = "Data de Lançamento", padrao = true)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATALANCAMENTO")
    private Calendar dataLancamento;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "ciencia")
    private boolean ciencia;

    @NotNull
    @Column(name = "titulo")
    private String titulo;

    public ProcessoAndamentos() {
        super();
        this.setCiencia(false);
    }

    public ProcessoAndamentos(Long id) {
        this();
        this.id = id;
    }

    public ProcessoAndamentos(Processo processo, Calendar dataCaptura, String titulo, String descricao) {
        this();
        this.setProcesso(processo);
        this.setDataCaptura(dataCaptura);
        this.setTitulo(titulo);
        this.setDescricao(descricao);
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return dataCaptura + " " + descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ProcessoAndamentos that = (ProcessoAndamentos) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
