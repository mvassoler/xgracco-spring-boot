package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * @author rianmachado
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Relation(collectionRelation = "tipos-garantias")
@Table(name = "TIPOGARANTIA", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao"}, name = "descTpGar")})
@RelatorioInterface(titulo = "Tipos de Garantia")
@SequenceGenerator(allocationSize = 1, name = "seqTipoGarantia", sequenceName = "SEQ_TIPOGARANTIA")
@Audited
@Data
@Builder
@AllArgsConstructor
public class TipoGarantia extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqTipoGarantia")
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO", length = 100, nullable = false)
    private String descricao;

    @RelatorioInterface(titulo = "Ativo")
    @Column(name = "ATIVO")
    private Boolean ativo;

    @Column(name = "MARCA")
    private Boolean marca;

    @Column(name = "MODELO")
    private Boolean modelo;

    @Column(name = "CHASSI")
    private Boolean chassi;

    @Column(name = "SERIE")
    private Boolean serie;

    @Column(name = "DESCRICAOMOTOR")
    private Boolean descricaoMotor;

    @Column(name = "ANOFABRICACAO")
    private Boolean anoFabricacao;

    @Column(name = "ANOMODELO")
    private Boolean anoModelo;

    @Column(name = "COR")
    private Boolean cor;

    @Column(name = "PLACA")
    private Boolean placa;

    @Column(name = "RENAVAM")
    private Boolean renavam;

    @Column(name = "TIPOVEICULO")
    private Boolean tipoVeiculo;

    @Column(name = "TIPOCOMBUSTIVEL")
    private Boolean tipoCombustivel;

    @Column(name = "ESTADOCONVERSAVAO")
    private Boolean estadoConservacao;

    @Column(name = "CODIGOMOLICAR")
    private Boolean codigoMolicar;

    @Column(name = "VALORMOLICAR")
    private Boolean valorMolicar;

    @Column(name = "DESCRICAOGARANTIA")
    private Boolean descricaoGarantia;

    @Column(name = "ESTADO")
    private Boolean estado;

    @Column(name = "CIDADE")
    private Boolean cidade;

    @Column(name = "VALORJUDICIAL")
    private Boolean valorJudicial;

    @Column(name = "VALOREXTRAJUDICIAL")
    private Boolean valorExtrajudicial;

    @Column(name = "ONUSADMINISTRACAO")
    private Boolean onusAdministracao;

    @Column(name = "PROPRIETARIO")
    private Boolean proprietario;

    @Column(name = "NUMEROMATRICULA")
    private Boolean numeroMatricula;

    @Column(name = "DATAMATRICULA")
    private Boolean dataMatricula;

    @Column(name = "NUMEROAPOLICE")
    private Boolean numeroApolice;

    @Column(name = "INICIOVIGENCIA")
    private Boolean inicioVigencia;

    @Column(name = "FIMVIGENCIA")
    private Boolean fimVigencia;

    @Column(name = "VALORSEGURO")
    private Boolean valorSeguro;

    @Column(name = "VALORPREMIO")
    private Boolean valorPremio;

    @Column(name = "STATUS")
    private Boolean status;


    @Transient
    private LogAuditoria logAuditoria;

    public TipoGarantia() {
    }

    public TipoGarantia(Long id) {
        this.id = id;
    }

    @QueryProjection
    public TipoGarantia(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public Long getId() {
        return id;
    }


    @Override
    public String getTextoLog() {
        return descricao;
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
        TipoGarantia that = (TipoGarantia) o;
        return Objects.equals(this.getId(), that.getId());
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
