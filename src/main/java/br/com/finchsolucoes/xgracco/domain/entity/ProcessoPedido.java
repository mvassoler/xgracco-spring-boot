package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.beans.views.PedidoView;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Table(name = "PEDIDO")
@Relation(collectionRelation = "pedidos")
@SequenceGenerator(allocationSize = 1, name = "seqPedido", sequenceName = "SEQ_PEDIDO")
@RelatorioInterface(titulo = "Pedido")
@Audited
@Data
@Builder
@AllArgsConstructor
public class ProcessoPedido extends Entidade implements EntidadeAuditada, ProcessoDependency {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPedido")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO")
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    private String descricao;

    @NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
    @RelatorioInterface(titulo = "Valor do Pedido", padrao = true)
    @Column(name = "VALOR", precision = 19, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "MULTAMEMO", length = 100)
    private String multaMemo;

    @NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "PERCENTUALMULTA", precision = 19, scale = 4)
    private BigDecimal percentualMulta;

    @NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "POSSIBILIDADEPERDA", precision = 19, scale = 4)
    private BigDecimal possibilidadePerdaPercent;

    @NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALORPROVISAO", precision = 19, scale = 2)
    private BigDecimal valorProvisao;

    @NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "MULTAVALORFIXO", precision = 19, scale = 2)
    private BigDecimal multaValorFixo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATACADASTRO", nullable = false, updatable = false)
    private Calendar dataCadastro;

    @Column(name = "MOTIVOALTERACAO", length = 100)
    private String motivoAlteracao;

    @Column(name = "EMBASAMENTOPROVISAO", length = 100)
    private String embasamentoProvisao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TIPO_PEDIDO")
    @RelatorioInterface(titulo = "Tipo do Pedido")
    private TipoPedido tipoPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_RISCO_CAUSA")
    @RelatorioInterface(titulo = "Risco Causa")
    private RiscoCausa riscoCausa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO"/*, insertable = false, updatable = false*/)
    @RelatorioInterface(titulo = "Processo")
    private Processo processo;

    @NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "SUCUMBENCIA", precision = 19, scale = 2)
    private BigDecimal sucumbencia;

    @NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "ENCARGOS", precision = 19, scale = 2)
    private BigDecimal encargos;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private ProcessoPedidoCenarios cenario;

    @Column(name = "HABILITAR_CENARIOS")
    private boolean cenariosHabilitados;

    @NotAudited
    @OneToMany(mappedBy = "processoPedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PedidoIndice> indices;

    @NotAudited
    @OneToMany(mappedBy = "processoPedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PedidoJuros> juros;

    @Transient
    private PedidoView pedidoView;

    @Transient
    private String correcaoDeFormat;

    @Transient
    private String correcaoAteFormat;

    @Transient
    private String jurosDeFormat;

    @Transient
    private String jurosAteFormat;

    @Transient
    private String dataCadastroFormat;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private String riscoDescricao;

    @Transient
    private String tipoDescricao;

    @Transient
    private String previaValores;

    public ProcessoPedido() {
    }

    @QueryProjection
    public ProcessoPedido(Long id) {
        this.id = id;
    }

    @QueryProjection
    public ProcessoPedido(Long id, BigDecimal valor) {
        this.id = id;
        this.valor = valor;
    }

    @QueryProjection
    public ProcessoPedido(Long id, Processo processo, ProcessoPedidoCenarios cenario){
        this.id = id;
        this.processo = processo;
        this.cenario = cenario;
    }

    @QueryProjection
    public ProcessoPedido(Long id, String descricao, BigDecimal valor, BigDecimal multaValorFixo,
                          BigDecimal percentualMulta, BigDecimal valorProvisao, BigDecimal sucumbencia, BigDecimal encargos) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.multaValorFixo = multaValorFixo;
        this.percentualMulta = percentualMulta;
        this.valorProvisao = valorProvisao;
        this.sucumbencia = sucumbencia;
        this.encargos = encargos;
    }

    @QueryProjection
    public ProcessoPedido(Long id, String descricao, BigDecimal valor, TipoPedido tipoPedido, RiscoCausa riscoCausa, Processo processo,
                          BigDecimal multaValorFixo, BigDecimal percentualMulta, String multaMemo, BigDecimal possibilidadePerdaPercent,
                          BigDecimal valorProvisao, String embasamentoProvisao, Calendar dataCadastro, String motivoAlteracao, BigDecimal sucumbencia,
                          BigDecimal encargos) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipoPedido = tipoPedido;
        this.riscoCausa = riscoCausa;
        this.processo = processo;
        this.multaValorFixo = multaValorFixo;
        this.percentualMulta = percentualMulta;
        this.multaMemo = multaMemo;
        this.possibilidadePerdaPercent = possibilidadePerdaPercent;
        this.valorProvisao = valorProvisao;
        this.embasamentoProvisao = embasamentoProvisao;
        this.dataCadastro = dataCadastro;
        this.motivoAlteracao = motivoAlteracao;
        this.sucumbencia = sucumbencia;
        this.encargos = encargos;
    }

    @QueryProjection
    public ProcessoPedido(Long id, String descricao, BigDecimal valor, TipoPedido tipoPedido, BigDecimal multaValorFixo, BigDecimal percentualMulta, String multaMemo, BigDecimal possibilidadePerdaPercent,
                          BigDecimal valorProvisao, String embasamentoProvisao, Calendar dataCadastro, String motivoAlteracao, BigDecimal sucumbencia,
                          BigDecimal encargos) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipoPedido = tipoPedido;
        this.multaValorFixo = multaValorFixo;
        this.percentualMulta = percentualMulta;
        this.multaMemo = multaMemo;
        this.possibilidadePerdaPercent = possibilidadePerdaPercent;
        this.valorProvisao = valorProvisao;
        this.embasamentoProvisao = embasamentoProvisao;
        this.dataCadastro = dataCadastro;
        this.motivoAlteracao = motivoAlteracao;
        this.sucumbencia = sucumbencia;
        this.encargos = encargos;
    }

    @QueryProjection
    public ProcessoPedido(Long id, String descricao, BigDecimal valor, TipoPedido tipoPedido, BigDecimal multaValorFixo, BigDecimal percentualMulta, String multaMemo, BigDecimal possibilidadePerdaPercent,
                          BigDecimal valorProvisao, String embasamentoProvisao, Calendar dataCadastro, String motivoAlteracao, BigDecimal sucumbencia,
                          BigDecimal encargos, boolean cenariosHabilitados, ProcessoPedidoCenarios cenario, Processo processo) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipoPedido = tipoPedido;
        this.multaValorFixo = multaValorFixo;
        this.percentualMulta = percentualMulta;
        this.multaMemo = multaMemo;
        this.possibilidadePerdaPercent = possibilidadePerdaPercent;
        this.valorProvisao = valorProvisao;
        this.embasamentoProvisao = embasamentoProvisao;
        this.dataCadastro = dataCadastro;
        this.motivoAlteracao = motivoAlteracao;
        this.sucumbencia = sucumbencia;
        this.encargos = encargos;
        this.cenariosHabilitados = cenariosHabilitados;
        this.cenario = cenario;
        this.processo = processo;
    }

    @QueryProjection
    public ProcessoPedido(Processo processo, BigDecimal valor, BigDecimal valorProvisao) {
        this.valor = valor;
        this.valorProvisao = valorProvisao;
    }

    @PrePersist
    protected void onCreate() {
        dataCadastro = Calendar.getInstance();
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
        ProcessoPedido processoPedido = (ProcessoPedido) o;
        return Objects.equals(this.getId(), processoPedido.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return descricao;
    }
}
