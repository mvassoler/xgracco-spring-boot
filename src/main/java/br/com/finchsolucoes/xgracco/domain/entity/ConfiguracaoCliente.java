package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoContagemDias;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoContagemDiasConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by jordano on 24/04/2018
 */
@Entity
@Table(name = "CONFIGURACAO_CLIENTE")
@Relation(collectionRelation = "configuracao-cliente")
@Audited
@Data
@Builder
@AllArgsConstructor
public class ConfiguracaoCliente implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", updatable = false)
    private Long id;

    //@NotNull
    @Column(name = "OPERACIONAL_OBRIGATORIO")
    private boolean operacionalObrigatorio;

    //@NotNull
    @Column(name = "PESSOA_DOCUMENTO_OBRIGATORIO")
    private boolean pessoaDocumentoObrigatorio;

    //@NotNull
    @Column(name = "TIPO_AGENDAMENTO_DIAS")
    @Convert(converter = EnumTipoContagemDiasConverter.class)
    private EnumTipoContagemDias tipoContagemDias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ESCRITORIO_PLANO_ECONOMICO")
    private Escritorio escritorioPlanoEconomico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ESCRITORIO_A")
    private Escritorio escritorioA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ESCRITORIO_B")
    private Escritorio escritorioB;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ESCRITORIO_C")
    private Escritorio escritorioC;

    @Column(name = "NUMDOCPROCDESPESASSOMENTENUM")
    private boolean numDocProcSomenteNumero;

    @Column(name = "NUMDOCPROCHONORARIOSOMENTENUM")
    private boolean numDocProcHonorarioSomenteNumero;


    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private Long percentualEscritorioA;
    @Transient
    private Long percentualEscritorioB;
    @Transient
    private Long percentualEscritorioC;
    @Transient
    private Long quantidadeDistribuicaoEscritorioA;
    @Transient
    private Long quantidadeDistribuicaoEscritorioB;
    @Transient
    private Long quantidadeDistribuicaoEscritorioC;

    public ConfiguracaoCliente() {
    }

    @QueryProjection
    public ConfiguracaoCliente(Long id, Escritorio escritorioPlanoEconomico){
        this.id = id;
        this.escritorioPlanoEconomico = escritorioPlanoEconomico;
    }

    @QueryProjection
    public ConfiguracaoCliente(Long id, Escritorio escritorioA,Escritorio escritorioB, Escritorio escritorioC){
        this.id = id;
        this.escritorioA = escritorioA;
        this.escritorioB = escritorioB;
        this.escritorioC = escritorioC;
    }

    @QueryProjection
    public ConfiguracaoCliente(Long id, boolean operacionalObrigatorio, boolean pessoaDocumentoObrigatorio, EnumTipoContagemDias tipoContagemDias, Escritorio escritorioPlanoEconomico, Escritorio escritorioA, Escritorio escritorioB, Escritorio escritorioC) {
        this.id = id;
        this.operacionalObrigatorio = operacionalObrigatorio;
        this.pessoaDocumentoObrigatorio = pessoaDocumentoObrigatorio;
        this.tipoContagemDias = tipoContagemDias;
        this.escritorioPlanoEconomico = escritorioPlanoEconomico;
        this.escritorioA = escritorioA;
        this.escritorioB = escritorioB;
        this.escritorioC = escritorioC;
    }

    @QueryProjection
    public ConfiguracaoCliente(Long id, boolean operacionalObrigatorio, boolean pessoaDocumentoObrigatorio, EnumTipoContagemDias tipoContagemDias,
                               Escritorio escritorioPlanoEconomico, Escritorio escritorioA, Escritorio escritorioB,
                               Escritorio escritorioC, boolean numDocProcSomenteNumero,
                               boolean numDocProcHonorarioSomenteNumero) {
        this.id = id;
        this.operacionalObrigatorio = operacionalObrigatorio;
        this.pessoaDocumentoObrigatorio = pessoaDocumentoObrigatorio;
        this.tipoContagemDias = tipoContagemDias;
        this.escritorioPlanoEconomico = escritorioPlanoEconomico;
        this.escritorioA = escritorioA;
        this.escritorioB = escritorioB;
        this.escritorioC = escritorioC;
        this.numDocProcSomenteNumero = numDocProcSomenteNumero;
        this.numDocProcHonorarioSomenteNumero = numDocProcHonorarioSomenteNumero;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return this.logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    public void setPercentuaisEscritorios(Long percentualEscritorioA, Long percentualEscritorioB, Long percentualEscritorioC){
        this.percentualEscritorioA = percentualEscritorioA;
        this.percentualEscritorioB = percentualEscritorioB;
        this.percentualEscritorioC = percentualEscritorioC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ConfiguracaoCliente configuracaoCliente = (ConfiguracaoCliente) o;
        return Objects.equals(this.getId(), configuracaoCliente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
