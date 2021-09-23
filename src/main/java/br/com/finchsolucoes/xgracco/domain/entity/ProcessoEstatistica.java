package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumProcessoTipoPerda;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumProcessoTipoPerdaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author Marcelo Aguiar
 */
@Entity
@Table(name = "PROCESSOESTATISTICA")
@SequenceGenerator(allocationSize = 1, name = "seqProcessoEstatistica", sequenceName = "SEQ_PROCESSOESTATISTICA")
@Data
@Builder
@AllArgsConstructor
public class ProcessoEstatistica extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqProcessoEstatistica")
    @Column(name = "ID")
    private Long id;

    @Column(name = "VALOR_CAUSA")
    private BigDecimal valorCausa;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_CADASTRO")
    private Calendar dataCadastro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA_CADASTRO")
    private Pessoa cadastradoPor;

    @Column(name = "TIPO_PERDA")
    @Convert(converter = EnumProcessoTipoPerdaConverter.class)
    private EnumProcessoTipoPerda tipoPerda;

    @Column(name = "PERDA_PORCENTAGEM")
    private BigDecimal porcentagem;

    @Column(name = "VALOR_CONDENACAO")
    private BigDecimal valorCondenacao;

    @Column(name = "VALOR_PAGO")
    private BigDecimal valorPago;

    @Column(name = "ANOTACAO")
    private String anotacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_ALTERACAO")
    private Calendar dataAlteracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA_ALTERACAO")
    private Pessoa alteradoPor;

    @Column(name = "MOTIVO_ALTERACAO")
    private String motivoAlteracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    public ProcessoEstatistica() {
    }

    public ProcessoEstatistica(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return new StringBuilder("Valor causa: ").append(valorCausa).append("| Pos. Perda: ").append(tipoPerda.toString()).append("| % perda: ").append(porcentagem).append("| AnotaÃ§Ã£o: ").append(anotacao).append("| Motivo alteração: ").append(motivoAlteracao).toString().replace("null", "-");
    }

    public Pessoa getAlteradoPor() {
        return alteradoPor;
    }

    public void setAlteradoPor(Pessoa alteradoPor) {
        this.alteradoPor = alteradoPor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ProcessoEstatistica that = (ProcessoEstatistica) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
